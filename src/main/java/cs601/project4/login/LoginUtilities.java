package cs601.project4.login;

import com.google.gson.Gson;
import cs601.project4.constant.LoginServerConstants;
import cs601.project4.model.Users;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.StringReader;
import java.util.Base64;
import java.util.Map;

/**
 * A utility class with several helper methods.
 */
public class LoginUtilities {
  private static final Gson gson = new Gson();

  /**
   * Hash the session ID to generate a nonce.
   * Uses Apache Commons Codec
   * See https://www.baeldung.com/sha-256-hashing-java
   *
   * @param  sessionId cookie
   * @return sha256hex
   */
  public static String generateNonce(String sessionId) {
    return DigestUtils.sha256Hex(sessionId);
  }

  /**
   * Generates the URL to make the initial request to the authorize API.
   *
   * @param clientId    String client id
   * @param state       String state
   * @param nonce       String nonce
   * @param redirectURI String redirect URI
   * @return url
   */
  public static String generateSlackAuthorizeURL(
      String clientId,
      String state,
      String nonce,
      String redirectURI
  ) {

    return String.format("https://%s/%s?%s=%s&%s=%s&%s=%s&%s=%s&%s=%s&%s=%s",
        LoginServerConstants.HOST,
        LoginServerConstants.AUTH_PATH,
        LoginServerConstants.RESPONSE_TYPE_KEY,
        LoginServerConstants.RESPONSE_TYPE_VALUE,
        LoginServerConstants.SCOPE_KEY,
        LoginServerConstants.SCOPE_VALUE,
        LoginServerConstants.CLIENT_ID_KEY,
        clientId,
        LoginServerConstants.STATE_KEY,
        state,
        LoginServerConstants.NONCE_KEY,
        nonce,
        LoginServerConstants.REDIRECT_URI_KEY,
        redirectURI
    );
  }

  /**
   * Generates the URL to exchange the initial auth for a token.
   *
   * @param clientId     String client id
   * @param clientSecret String client secret
   * @param code         String code
   * @param redirectURI  String redirect URI
   * @return url
   */
  public static String generateSlackTokenURL(String clientId, String clientSecret, String code, String redirectURI) {
    return String.format("https://%s/%s?%s=%s&%s=%s&%s=%s&%s=%s",
        LoginServerConstants.HOST,
        LoginServerConstants.TOKEN_PATH,
        LoginServerConstants.CLIENT_ID_KEY,
        clientId,
        LoginServerConstants.CLIENT_SECRET_KEY,
        clientSecret,
        LoginServerConstants.CODE_KEY,
        code,
        LoginServerConstants.REDIRECT_URI_KEY,
        redirectURI
    );
  }

  /**
   * Convert a JSON-formatted String to a Map.
   *
   * @param jsonString String json
   * @return map
   */
  public static Map<String, Object> jsonStrToMap(String jsonString) {
    return gson.fromJson(new StringReader(jsonString), Map.class);
  }

  /**
   * Verify the response from the token API.
   * If successful, returns a ClientInfo object with information about the authed client.
   * Returns null if not successful.
   *
   * @param map       HashMap String Object
   * @param sessionId String cookie
   * @return ClientInfo object
   */
  public static Users verifyTokenResponse(Map<String, Object> map, String sessionId) {
    /* verify ok: true */
    if(!map.containsKey(LoginServerConstants.OK_KEY) ||
        !(boolean)map.get(LoginServerConstants.OK_KEY)) {
      return null;
    }

    /* verify state is the users session cookie id */
    if(!map.containsKey(LoginServerConstants.STATE_KEY) ||
        !map.get(LoginServerConstants.STATE_KEY).equals(sessionId)) {
      System.out.println(map.get(LoginServerConstants.STATE_KEY));
      System.out.println(sessionId);
      return null;
    }

    /* retrieve and decode id_token */
    String idToken = (String)map.get("id_token");
    Map<String, Object> payloadMap = decodeIdTokenPayload(idToken);

    /* verify nonce */
    String expectedNonce = generateNonce(sessionId);
    String actualNonce = (String) payloadMap.get(LoginServerConstants.NONCE_KEY);
    if(!expectedNonce.equals(actualNonce)) {
      return null;
    }

    /* extract data from response */
    String username = (String) payloadMap.get(LoginServerConstants.NAME_KEY);
    String email = (String) payloadMap.get(LoginServerConstants.EMAIL_KEY);
    return new Users(username, email);
  }

  /**
   *
   * From the Slack documentation:
   * id_token is a standard JSON Web Token (JWT). You can decode it with off-the-shelf libraries in any programming
   * language, and most packages that handle OpenID will handle JWT decoding.
   *
   * Method decodes the String id_token and returns a Map with the contents of the payload.
   *
   * @param idToken user ID token
   * @return map
   */
  private static Map<String, Object> decodeIdTokenPayload(String idToken) {
    /* Decoding process taken from:
     * https://www.baeldung.com/java-jwt-token-decode
     */
    String[] chunks = idToken.split("\\.");
    Base64.Decoder decoder = Base64.getDecoder();

    String header = new String(decoder.decode(chunks[0]));
    String payload = new String(decoder.decode(chunks[1]));

    /* convert the id_token payload to a map */
    return gson.fromJson(new StringReader(payload), Map.class);
  }
}
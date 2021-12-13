package cs601.project4.unit_test;

import static org.junit.jupiter.api.Assertions.*;

import cs601.project4.login.LoginUtilities;
import cs601.project4.web.WebUtilities;
import java.sql.Timestamp;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class UtilitiesTest {

  /* Web Util */
  @Test
  public void testGetTimestampString() {
    Timestamp timestamp = Timestamp.valueOf("2021-12-01 21:23:33.564");
    assertEquals("2021-12-01 21:23", WebUtilities.getTimestampString(timestamp));
  }


  /* Login Util */
  @Test
  public void testGenerateSlackAuthorizeURL() {
    String url = LoginUtilities.generateSlackAuthorizeURL("3464212158.2674770528882", "5126c509-9a27-47c6-b431-30b94b4f0268", "ancic314894", "https://slack/redirect_uri");
    assertEquals("https://slack.com/openid/connect/authorize?response_type=code&scope=openid%20profile%20email&client_id=3464212158.2674770528882&state=5126c509-9a27-47c6-b431-30b94b4f0268&nonce=ancic314894&redirect_uri=https://slack/redirect_uri", url);
  }

  @Test
  public void testGenerateNonce() {
    String nonce = LoginUtilities.generateNonce("5126c509-9a27-47c6-b431-30b94b4f0268");
    assertEquals("d9955d00400500cfbc375f256a303d510301d57e047f4b1dae92d4db15a87529", nonce);
  }

  @Test
  public void testGenerateSlackTokenURL() {
    String url = LoginUtilities.generateSlackTokenURL("3464212157.2674770528882","6897aab8047ec1610f5290ca16103b0e", "3464212157.2844063677169.3dcd9fe13822a5c6c272fd13f3f4585768a989b89f3d0bb2c120f9b4bc37706e", "https://1f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/home");
    assertEquals("https://slack.com/api/openid.connect.token?client_id=3464212157.2674770528882&client_secret=6897aab8047ec1610f5290ca16103b0e&code=3464212157.2844063677169.3dcd9fe13822a5c6c272fd13f3f4585768a989b89f3d0bb2c120f9b4bc37706e&redirect_uri=https://1f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/home", url);
  }

  @Test
  public void testConvertJsonToMap() {
    Map<String, Object> response = LoginUtilities.jsonStrToMap("{\"ok\":true,\"access_token\":\"xoxp-2464212157-1058774410546-2721128926819-af0c89fd6e69cc11fe6dd1dd35f289e9\",\"token_type\":\"Bearer\",\"id_token\":\"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Im1CMk1BeUtTbjU1NWlzZDBFYmRoS3g2bmt5QWk5eExxOHJ2Q0ViX25PeVkifQ.eyJpc3MiOiJodHRwczpcL1wvc2xhY2suY29tIiwic3ViIjoiVTAxMVFOU0MyRzIiLCJhdWQiOiIyNDY0MjEyMTU3LjI2NzQ3NzA1Mjg4ODIiLCJleHAiOjE2MzkzNjQ1ODIsImlhdCI6MTYzOTM2NDI4MiwiYXV0aF90aW1lIjoxNjM5MzY0MjgyLCJub25jZSI6ImViY2YzNjc2NjllY2Y3ZjNjODhmMGM5MGE1YjIwYTljMzE3N2VkMmVhYWZiOTVlNjkzMjcwNDE1NTViMjg3MjIiLCJhdF9oYXNoIjoibURxOVFQN01yYU1vM2UyMV9WaTlsQSIsImh0dHBzOlwvXC9zbGFjay5jb21cL3RlYW1faWQiOiJUMDJETjY4NE0iLCJodHRwczpcL1wvc2xhY2suY29tXC91c2VyX2lkIjoiVTAxMVFOU0MyRzIiLCJlbWFpbCI6Im10YW5pYUBkb25zLnVzZmNhLmVkdSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJkYXRlX2VtYWlsX3ZlcmlmaWVkIjoxNjM1ODMyOTIwLCJsb2NhbGUiOiJlbi1VUyIsIm5hbWUiOiJNYXJpc2EgVGFuaWEiLCJwaWN0dXJlIjoiaHR0cHM6XC9cL2F2YXRhcnMuc2xhY2stZWRnZS5jb21cLzIwMjEtMDYtMDdcLzIxNTkzNDI0OTA4MzNfMDU0OTUzMTU0MTI5MDAxYzc5MmJfNTEyLmpwZyIsImdpdmVuX25hbWUiOiJNYXJpc2EiLCJmYW1pbHlfbmFtZSI6IlRhbmlhIiwiaHR0cHM6XC9cL3NsYWNrLmNvbVwvdGVhbV9uYW1lIjoiVVNGQ1MiLCJodHRwczpcL1wvc2xhY2suY29tXC90ZWFtX2RvbWFpbiI6InVzZmNzIiwiaHR0cHM6XC9cL3NsYWNrLmNvbVwvdGVhbV9pbWFnZV8yMzAiOiJodHRwczpcL1wvYXZhdGFycy5zbGFjay1lZGdlLmNvbVwvMjAxNi0wOC0xMFwvNjgyNDM2ODYyMjRfMzgyMmU2YmRiZTllMDJmZTFhN2RfMjMwLnBuZyJ9.OJm01V65AQsblIReYX0J6u0fFK3NQf5RE__XaqDFWZan2APFDvr_ipuam3LkPUlBxwxJOqXqdjSDC9ZrrRRB0Erl9C8oKYKfY8JYT7ESJUBIUjxZD3E4nYvuWgAB2QNjIVeF0LDXnw3SabB7tv0P5YXapgbBGiDap2K6pOSGCcEeif2h0Naw1POQnT4IPpsQ0gQUc8XZfNFIE-dvJF7XyKNUPVWL4DySGeDG-u7Vuyojzn1mnJqUUpxSGhf9_Rt0stKkjY4Gh2PwfW3lqzBg25aGignsBYO1mARdSCopXA0Bi90nMqK5huHPd5aQYufmIVHa7bG8_TGHCPe8_iHK5g\",\"state\":\"752bc3d3-6ba3-467e-9315-2d7836317046\"}");
    assertTrue((Boolean) response.get("ok"));
  }
}

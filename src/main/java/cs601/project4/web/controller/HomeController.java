package cs601.project4.web.controller;
import com.google.gson.Gson;
import cs601.project4.database.DBManager;
import cs601.project4.constant.LoginServerConstants;
import java.sql.Connection;
import java.sql.SQLException;
import cs601.project4.model.ClientInfo;
import cs601.project4.login.HTTPFetcher;
import cs601.project4.login.LoginUtilities;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for landing page
 *
 * @author marisatania
 */
@Controller
public class HomeController {
  private final Logger logger = LoggerFactory.getLogger(HomeController.class);
  @Value("${slack.config.redirect_uri}")
  private String redirectURI;

  @Value("${slack.config.client_id}")
  private String clientID;

  @Value("${slack.config.client_secret}")
  private String clientSecret;

  /**
   * Handles landing page
   *
   * @return index
   */
  @GetMapping(value={"/"})
  public String getLandingPage() {
    return "index";
  }

  /**
   * Handles homepage after login
   *
   * @return home
   */
  @GetMapping("/home")
  public String home(Model model, HttpServletRequest request) {
    /* Retrieve the ID of this session */
    String sessionId = request.getSession(true).getId();
    Object clientInfoObj =
        request.getSession().getAttribute(LoginServerConstants.CLIENT_INFO_KEY);

    /* Check if already authenticated */
    if (clientInfoObj != null) {
      System.out.println("Client with session ID %s already exists.\n");
      return "redirect:/internal-user";
    }

    /* Retrieve the code provided by Slack */
    String code = request.getParameter(LoginServerConstants.CODE_KEY);

    /* Generate the url to use to exchange the code for a token: */
    String url = LoginUtilities.generateSlackTokenURL(clientID, clientSecret, code, redirectURI);

    /* Make the request to the token API */
    String responseString = HTTPFetcher.doGet(url, null);
    Map<String, Object> response = LoginUtilities.jsonStrToMap(responseString);

    ClientInfo clientInfo = LoginUtilities.verifyTokenResponse(response, sessionId);

    if (clientInfo == null) {
      return "redirect:/login-error";
    }

    /* Add to database */
    try (Connection con = DBManager.getConnection()) {
      if (!DBManager.insertUser(con, clientInfo.getName(), clientInfo.getEmail())) {
        logger.info("User already exists in database.");
      }
      DBManager.insertUserSessionID(con, clientInfo.getName(), sessionId);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    request.getSession().setAttribute(LoginServerConstants.CLIENT_INFO_KEY, new Gson().toJson(clientInfo));
    model.addAttribute("name", clientInfo.getName());
    return "home";
  }

  /**
   * Handles users that are already being authenticated
   *
   * @return internal-user
   */
  @GetMapping(value={"/internal-user"})
  public String internalUser() {
    return "internal-user";
  }


}

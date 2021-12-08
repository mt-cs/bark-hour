package cs601.project4.web.controller;

import cs601.project4.constant.LoginServerConstants;
import cs601.project4.database.DatabaseManager;
import cs601.project4.login.LoginUtilities;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for Login features
 *
 * @author marisatania
 */
@Controller
public class LoginController {

  @Value("${slack.config.redirect_uri}")
  private String redirect_uri;

  @Value("${slack.config.client_id}")
  private String client_id;

  private final Logger logger = LoggerFactory.getLogger(LoginController.class);

  /**
   * Handles login page
   *
   * @param model   Model ui
   * @param request HttpServletRequest
   * @return        index
   */
  @GetMapping(value={"/login"})
  public String getLogin(Model model, HttpServletRequest request) {
    String sessionId = request.getSession(true).getId();
    Object clientInfoObj =
        request.getSession().getAttribute(LoginServerConstants.CLIENT_INFO_KEY);

    if(clientInfoObj != null) {
      System.out.println("Client with session ID %s already exists.\n");
      return "redirect:/internal-user";
    }

    String nonce = LoginUtilities.generateNonce(sessionId);
    String url = LoginUtilities.generateSlackAuthorizeURL(
        client_id,
        sessionId,
        nonce,
        redirect_uri);
    logger.info("Slack Authorize URL: " + url);

    model.addAttribute("url", url);
    return "login";
  }

  /**
   * Handles login error
   *
   * @param request HttpServletRequest
   * @return        login-error
   */
  @GetMapping(value={"/login-error"})
  public String loginError(HttpServletRequest request) {
    request.getSession().invalidate();
    return "login-error";
  }

  /**
   * Handles a request to sign out
   *
   * @param request HTTP Servlet Request
   * @return logout
   */
  @GetMapping(value={"/logout"})
  public String logout(HttpServletRequest request) {
    String sessionId = request.getSession(true).getId();
    try (Connection con = DatabaseManager.getConnection()) {
      DatabaseManager.deleteUserSessionID(con, sessionId);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    request.getSession().invalidate();
    return "logout";
  }
}

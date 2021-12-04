package cs601.project4.web;

import cs601.project4.constant.LoginConstant;
import cs601.project4.login.LoginUtilities;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for login
 *
 * @author marisatania
 */
@Controller
public class LoginController {
  private final Logger logger = LoggerFactory.getLogger(LoginController.class);

  @Value("${slack.config.redirect_uri}")
  private String redirect_uri;

  @Value("${slack.config.client_id}")
  private String client_id;

  /**
   * Handles login page
   *
   * @param model   Model ui
   * @param request HttpServletRequest
   * @return index
   */
  @GetMapping(value={"/", "/login"})
  public String index(Model model, HttpServletRequest request) {
    // retrieve the ID of this session
    String sessionId = request.getSession(true).getId();
    Object clientInfoObj =
        request.getSession().getAttribute(LoginConstant.CLIENT_INFO_KEY);
    if(clientInfoObj != null) {
      // already authed, no need to log in
      System.out.println("Client with session ID %s already exists.\n");
      return "redirect:/internaluser";
    }

    String nonce = LoginUtilities.generateNonce(sessionId);

    // determine whether the user is already authenticated
    String url = LoginUtilities.generateSlackAuthorizeURL(
        client_id,
        sessionId,
        nonce,
        redirect_uri);
    logger.info("Slack Authorize URL: " , url);

    model.addAttribute("url", url);
    return "index";
  }

  /**
   * Handles login error
   *
   * @param request HttpServletRequest
   * @return loginerror
   */
  @GetMapping(value={"/loginerror"})
  public String loginError(HttpServletRequest request) {
    request.getSession().invalidate();
    return "loginerror";
  }
}

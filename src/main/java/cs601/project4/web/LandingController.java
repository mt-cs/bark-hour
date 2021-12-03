package cs601.project4.web;

import cs601.project4.constant.LoginConstant;
import cs601.project4.login.LoginServerConstants;
import cs601.project4.login.utilities.Config;
import cs601.project4.login.utilities.LoginUtilities;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class LandingController {
  @GetMapping("/")
  public String index(Model model, HttpServletRequest request) {
    // retrieve the ID of this session
    String sessionId = request.getSession(true).getId();
    Object clientInfoObj =
        request.getSession().getAttribute(LoginConstant.CLIENT_INFO_KEY);
    if(clientInfoObj != null) {
      // already authed, no need to log in
      System.out.println("Client with session ID %s already exists.\n");
      return "redirect:/home";
    }
    String state = sessionId;

    // retrieve the config info from the context
    Config config = (Config) request.getServletContext().getAttribute(LoginServerConstants.CONFIG_KEY);

    /** From the Open ID spec:
     * nonce
     * OPTIONAL. String value used to associate a Client session with an ID Token, and to mitigate
     * replay attacks. The value is passed through unmodified from the Authentication Request to
     * the ID Token. Sufficient entropy MUST be present in the nonce values used to prevent attackers
     * from guessing values. For implementation notes, see Section 15.5.2.
     */
    String nonce = LoginUtilities.generateNonce(state);

    // Generate url for request to Slack
    String url = LoginUtilities.generateSlackAuthorizeURL(config.getClient_id(),
        state,
        nonce,
        config.getRedirect_url());

    System.out.println(url);

    model.addAttribute("url", url);
    return "index";
  }
}
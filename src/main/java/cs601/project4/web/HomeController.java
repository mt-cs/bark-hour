package cs601.project4.web;
import cs601.project4.constant.LoginConstant;
//import jakarta.servlet.http.HttpServletRequest;
import cs601.project4.login.LoginServerConstants;
import cs601.project4.login.utilities.ClientInfo;
import cs601.project4.login.utilities.HTTPFetcher;
import cs601.project4.login.utilities.LoginUtilities;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @Value("${slack.config.redirect_uri}")
  private String redirect_uri;

  @Value("${slack.config.client_id}")
  private String client_id;

  @Value("${slack.config.client_id}")
  private String client_secret;

  @GetMapping("/home")
  public String home(Model model, HttpServletRequest request) {
    // retrieve the ID of this session
    String sessionId = request.getSession(true).getId();
    Object clientInfoObj =
        request.getSession().getAttribute(LoginConstant.CLIENT_INFO_KEY);
    if(clientInfoObj != null) {
      // already authed, no need to log in
      System.out.println("Client with session ID %s already exists.\n");
      return "redirect:/";
    }
    // retrieve the code provided by Slack
    String code = request.getParameter(LoginServerConstants.CODE_KEY);

    // generate the url to use to exchange the code for a token:
    // After the user successfully grants your app permission to access their Slack profile,
    // they'll be redirected back to your service along with the typical code that signifies
    // a temporary access code. Exchange that code for a real access token using the
    // /openid.connect.token method.
    String url = LoginUtilities.generateSlackTokenURL(client_id, client_secret, code, redirect_uri);

    // Make the request to the token API
    String responseString = HTTPFetcher.doGet(url, null);
    Map<String, Object> response = LoginUtilities.jsonStrToMap(responseString);

    ClientInfo clientInfo = LoginUtilities.verifyTokenResponse(response, sessionId);

    if(clientInfo == null) {

    }
    model.addAttribute("name", "Marisa");
    return "home";
  }
}

package cs601.project4.web;
import cs601.project4.constant.LoginConstant;
//import jakarta.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
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
    model.addAttribute("name", "Marisa");
    return "home";
  }
}
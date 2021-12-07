package cs601.project4.web;

import javax.servlet.http.HttpServletRequest;
import model.ClientInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

/**
 * Controller for user
 *
 * @author marisatania
 */
@Controller
public class UserController {
  @GetMapping(value={"/user-account"})
  public String userAccount(Model model, HttpServletRequest request, ClientInfo clientInfo) {

    model.addAttribute("userid", clientInfo.getUserId());
    model.addAttribute("name", clientInfo.getName());
    model.addAttribute("email", clientInfo.getEmail());
    model.addAttribute("userid", clientInfo.getUserId());
    model.addAttribute("location", clientInfo.getLocation());
    model.addAttribute("reg_date", clientInfo.getRegistrationDate());

    return "user-account";
  }
}

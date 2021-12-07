package cs601.project4.web;

import cs601.project4.JDBC.DatabaseManager;
import cs601.project4.constant.LoginConstant;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import model.ClientInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping(value={"/user-account"})
  public String userAccount(Model model, HttpServletRequest request) {

    // Retrieve the ID of this session
    String sessionId = request.getSession(true).getId();

    // Add to database
    try (Connection con = DatabaseManager.getConnection()) {
      ResultSet results = DatabaseManager.selectUser(con, sessionId);
      while(results.next()) {
        model.addAttribute("userid", results.getInt("userid"));
        model.addAttribute("username", results.getString("username"));
        model.addAttribute("email", results.getString("email"));
        model.addAttribute("location", results.getString("location"));
        model.addAttribute("reg_date", results.getString("reg_date"));
//        System.out.printf("Name: %s\n", results.getString("name"));
//        System.out.printf("Extension: %s\n", results.getInt("extension"));
//        System.out.printf("Email: %s\n", results.getString("email"));
//        System.out.printf("Start Date: %s\n", results.getString("startdate"));
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }


//    ClientInfo clientInfo =

    return "user-account";
  }
}

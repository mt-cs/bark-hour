package cs601.project4.web;

import cs601.project4.JDBC.DatabaseManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for user
 *
 * @author marisatania
 */
@Controller
public class UserController {
  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping(value={"/account"})
  public String userAccount(Model model, HttpServletRequest request) {

    // Retrieve the ID of this session
    String sessionId = request.getSession(true).getId();

    // Add to database
    try (Connection con = DatabaseManager.getConnection()) {
      ResultSet results = DatabaseManager.selectUser(con, sessionId);
      while(results.next()) {
        model.addAttribute("username", results.getString("username"));
        model.addAttribute("email", results.getString("email"));
        model.addAttribute("location", results.getString("location"));
        model.addAttribute("reg_date", results.getString("reg_date"));
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "account";
  }

  @GetMapping(value={"/profile"})
  public String editProfile(Model model, HttpServletRequest request) {

    // Retrieve the ID of this session
    String sessionId = request.getSession(true).getId();

    try (Connection con = DatabaseManager.getConnection()) {
      ResultSet results = DatabaseManager.selectUser(con, sessionId);
      while(results.next()) {
        model.addAttribute("username", results.getString("username"));
        model.addAttribute("email", results.getString("email"));
        model.addAttribute("location", results.getString("location"));
        model.addAttribute("reg_date", results.getString("reg_date"));
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "profile";
  }

  /**
   * Handles successful profile update
   */
  @PostMapping(value={"/profile-update"})
  public String updateProfileSubmit(
      @RequestParam("username") String userName ,
      @RequestParam("email") String email,
      @RequestParam("location") String location) {
    try (Connection con = DatabaseManager.getConnection()) {
      DatabaseManager.updateUser(con, userName, email, location);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "profile-update";
  }

  /**
   * Handles get profile-update
   */
  @GetMapping(value={"/profile-update"})
  public String updateProfileForm() {
    return "profile-update";
  }

}

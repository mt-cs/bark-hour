package cs601.project4.web.controller;

import cs601.project4.constant.UserConstants;
import cs601.project4.database.DBManager;
import cs601.project4.database.DBUser;
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

//TODO: ADD EDIT NAME FEATURE

/**
 * Controller for user services
 *
 * @author marisatania
 */
@Controller
public class UserController {
  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping(value={"/users-profile"})
  public String userAccount(Model model, HttpServletRequest request) {
    String sessionId = request.getSession(true).getId();

    try (Connection con = DBManager.getConnection()) {
      ResultSet results = DBUser.selectUser(con, sessionId);
      while(results.next()) {
        model.addAttribute(UserConstants.USERNAME, results.getString(UserConstants.USERNAME));
        model.addAttribute(UserConstants.EMAIL, results.getString(UserConstants.EMAIL));
        model.addAttribute(UserConstants.LOCATION, results.getString(UserConstants.LOCATION));
        model.addAttribute(UserConstants.REG_DATE, results.getString(UserConstants.REG_DATE));
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "users-profile";
  }

  @GetMapping(value={"/users-profile-form"})
  public String editProfile(Model model, HttpServletRequest request) {
    String sessionId = request.getSession(true).getId();

    try (Connection con = DBManager.getConnection()) {
      ResultSet results = DBUser.selectUser(con, sessionId);
      while(results.next()) {
        model.addAttribute(UserConstants.USERNAME, results.getString(UserConstants.USERNAME));
        model.addAttribute(UserConstants.EMAIL, results.getString(UserConstants.EMAIL));
        model.addAttribute(UserConstants.LOCATION, results.getString(UserConstants.LOCATION));
        model.addAttribute(UserConstants.REG_DATE, results.getString(UserConstants.REG_DATE));
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "users-profile-form";
  }

  /**
   * Handles successful profile update
   */
  @PostMapping(value={"/users-profile-confirmation"})
  public String updateProfileSubmit(
      @RequestParam(UserConstants.USERNAME) String userName ,
      @RequestParam(UserConstants.EMAIL) String email,
      @RequestParam(UserConstants.LOCATION) String location) {

    try (Connection con = DBManager.getConnection()) {
      DBUser.updateUser(con, userName, email, location);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "users-profile-confirmation";
  }

  /**
   * Handles get profile-update
   */
  @GetMapping(value={"/users-profile-confirmation"})
  public String updateProfileForm() {
    return "users-profile-confirmation";
  }
}

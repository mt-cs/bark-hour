package cs601.project4.web.controller;

import static cs601.project4.web.WebUtilities.notifyFailedQuery;
import static cs601.project4.web.WebUtilities.validateLogin;

import cs601.project4.constant.NotificationConstants;
import cs601.project4.constant.UserConstants;
import cs601.project4.database.DBManager;
import cs601.project4.database.DBSessionId;
import cs601.project4.database.DBUser;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for user services
 *
 * @author marisatania
 */
@Controller
public class UserController {
  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  /**
   * Displays user profile
   *
   * @param model   Model
   * @param request HTTP Request
   * @return users-profile.html
   */
  @GetMapping(value={"/users-profile"})
  public String getUserAccount(Model model, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }
    String sessionId = session.getId();

    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      ResultSet results = DBUser.selectUser(con, sessionId, userId);
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

  /**
   * Get update users profile form
   *
   * @param model   Model
   * @param request HTTP Request
   * @return users-profile-form.html
   */
  @GetMapping(value={"/users-profile-form"})
  public String updateProfile(Model model, HttpServletRequest request) {

    String sessionId = validateLogin(request);

    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      ResultSet results = DBUser.selectUser(con, sessionId, userId);

      if(results.next()) {
        model.addAttribute(
            UserConstants.USERNAME,
            results.getString(UserConstants.USERNAME));

        model.addAttribute(
            UserConstants.EMAIL,
            results.getString(UserConstants.EMAIL));

        model.addAttribute(
            UserConstants.LOCATION,
            results.getString(UserConstants.LOCATION));

        model.addAttribute(
            UserConstants.REG_DATE,
            results.getString(UserConstants.REG_DATE));
      }

    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "users-profile-form";
  }

  /**
   * Handles successful profile update
   *
   * @param userName String user name
   * @param email    String user email
   * @param location String location
   * @return users-profile-confirmation.html
   */
  @PostMapping(value={"/users-profile-confirmation"})
  public String updateProfileSubmit(
      @RequestParam(UserConstants.USERNAME) String userName ,
      @RequestParam(UserConstants.EMAIL) String email,
      @RequestParam(UserConstants.LOCATION) String location,
      HttpServletRequest request,
      Model model) {

    String sessionId = validateLogin(request);

    try (Connection con = DBManager.getConnection()) {

      int userId = DBSessionId.getUserId(con, sessionId);

      if (!DBUser.updateUser(con, userName, email, location, userId)) {
        notifyFailedQuery(model, NotificationConstants.NOTIFY_USER_UPDATE_FAIL);
        return "users-profile-confirmation";
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    notifyFailedQuery(model, NotificationConstants.NOTIFY_USER_UPDATE_SUCCESS);
    return "users-profile-confirmation";
  }
}

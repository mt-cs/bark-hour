package cs601.project4.web.controller;

import cs601.project4.constant.EventConstants;
import cs601.project4.constant.UserConstants;
import cs601.project4.database.DBEvent;
import cs601.project4.database.DBManager;
import cs601.project4.database.DBSessionId;
import cs601.project4.database.DBTicket;
import cs601.project4.database.DBUser;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for ticket features
 *
 * @author marisatania
 */
@Controller
public class TicketController {
  private final Logger logger = LoggerFactory.getLogger(TicketController.class);

  /**
   * Displays ticket purchase history
   *
   * @param numTickets number of tickets
   * @return users-profile-confirmation.html
   */
  @PostMapping(value={"/ticket-purchase/{eventId}"})
  public String updateProfileSubmit(
      @RequestParam(EventConstants.NUM_TICKET) int numTickets,
      @PathVariable int eventId,
      HttpServletRequest request) {
    String sessionId = request.getSession(true).getId();

    if (numTickets < 1) {
      logger.warn("Number of tickets has to be at least 1");
      return "redirect:/error-400";
    }

    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      DBTicket.buyTickets(con, userId, eventId, numTickets);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "ticket-purchase";
  }

  /**
   * Handles users that are already being authenticated
   *
   * @return internal-user
   */
  @GetMapping(value={"/ticket"})
  public String getPurchaseTicketForm(Model model, HttpServletRequest request) {
    try (Connection con = DBManager.getConnection()) {
      String sessionId = request.getSession(true).getId();
      int userId = DBSessionId.getUserId(con, sessionId);
      int eventId = DBEvent.getEventId(con, userId);
      model.addAttribute(EventConstants.EVENT_ID, eventId);
      model.addAttribute(UserConstants.USER_ID, userId);
      model.addAttribute(EventConstants.NUM_TICKET, 1);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "ticket";
  }
}

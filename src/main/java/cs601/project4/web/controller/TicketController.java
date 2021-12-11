package cs601.project4.web.controller;

import cs601.project4.constant.EventConstants;
import cs601.project4.constant.UserConstants;
import cs601.project4.database.DBEvent;
import cs601.project4.database.DBManager;
import cs601.project4.database.DBSessionId;
import cs601.project4.database.DBTicket;
import cs601.project4.database.DBUser;
import cs601.project4.model.UserTicket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
   * Get purchase ticket form
   *
   * @return ticket
   */
  @GetMapping(value={"/ticket/{eventId}"})
  public String getPurchaseTicketForm(
      Model model,
      HttpServletRequest request,
      @PathVariable int eventId) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }
    String sessionId = session.getId();

    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      model.addAttribute(EventConstants.EVENT_ID, eventId);
      model.addAttribute(UserConstants.USER_ID, userId);
      model.addAttribute(EventConstants.NUM_TICKET, 1);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "ticket";
  }

  /**
   * Displays ticket purchase history
   *
   * @param numTickets number of tickets
   * @return users-profile-confirmation.html
   */
  @PostMapping(value={"/ticket-purchase/{eventId}"})
  public String purchaseTicket(
      @RequestParam(EventConstants.NUM_TICKET) int numTickets,
      @PathVariable int eventId,
      HttpServletRequest request) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }
    String sessionId = session.getId();

    if (numTickets < 1) {
      logger.warn("Number of tickets has to be at least 1");
      return "redirect:/error-400";
    }

    try (Connection con = DBManager.getConnection()) {
      int eventUserId = DBEvent.getUserIdByEventId(con, eventId);
      int availTicket = DBTicket.getTicketAvail(con, eventId);
      if (availTicket < numTickets) {
        logger.warn("Not enough space available.");
        return "redirect:/error-400"; //TODO: create status update page
      }
      int userId = DBSessionId.getUserId(con, sessionId);
      if (eventUserId == userId) {
        logger.warn("You are the owner of this event.");
        return "redirect:/error-400";
      }
      if (!DBTicket.buyTickets(con, userId, eventId, eventUserId, numTickets)){
        notifyFailedPurchase();
        return "redirect:/error-400";
      } else {
        int curAvailTicket = DBTicket.countTickets(con, eventUserId, eventId);
        if (curAvailTicket == availTicket || curAvailTicket == -1) {
          notifyFailedPurchase();
          return "redirect:/error-400";
        } else {
          DBTicket.updateTicketAvail(con, curAvailTicket, eventId);
          int purchasedSoFar = DBTicket.getTicketPurchased(con, eventId);
          if (purchasedSoFar == -1) {
            notifyFailedPurchase();
            return "redirect:/error-400";
          }
          DBTicket.updateTicketPurchased(con,purchasedSoFar + numTickets, eventId);
        }
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "ticket-purchase";
  }

  /**
   * Get transfer ticket form
   *
   * @return ticket-transfer
   */
  @GetMapping(value={"/ticket-transfer/{eventId}"})
  public String getTransferTicketForm(
      Model model,
      HttpServletRequest request,
      @PathVariable int eventId) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }
    String sessionId = session.getId();

    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      model.addAttribute(EventConstants.EVENT_ID, eventId);
      model.addAttribute(UserConstants.USER_ID, userId);
      model.addAttribute(EventConstants.NUM_TICKET, 1);
      model.addAttribute(UserConstants.EMAIL, "abc@gmail.com");
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "ticket-transfer";
  }

  /**
   * Transfer Ticket
   *
   * @param numTickets number of tickets
   * @return users-profile-confirmation.html
   */
  @PostMapping(value={"/ticket-transfer-status/{eventId}"})
  public String transferTicket(
      @RequestParam(EventConstants.NUM_TICKET) int numTickets,
      @RequestParam(UserConstants.EMAIL) String email,
      @PathVariable int eventId,
      HttpServletRequest request) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }
    String sessionId = session.getId();

    if (numTickets < 1) {
      logger.warn("Number of tickets has to be at least 1");
      return "redirect:/error-400";
    }

    try (Connection con = DBManager.getConnection()) {

      int userId = DBSessionId.getUserId(con, sessionId);
      int recipientId = DBUser.getUserId(con, email);
      if (recipientId == -1) {
        logger.warn("Recipient email is not registered.");
        return "redirect:/error-400";
      }
      if (userId == recipientId) {
        logger.warn("Can't transfer to owner");
        return "redirect:/error-400";
      }
      int availTicket = DBTicket.getTicketAvail(con, eventId);
      if (availTicket < numTickets) {
        logger.warn("Not enough tickets reserved.");
        return "redirect:/error-400"; //TODO: create status update page
      }
      if (!DBTicket.transferTickets(con, userId, recipientId, eventId, numTickets)){
        logger.warn("Ticket transfer failed.");
        return "redirect:/error-400";
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "ticket-transfer-status";
  }

  private void notifyFailedPurchase() {
    logger.warn("Ticket purchase failed.");
  }


  /**
   * Display all user's tickets
   *
   * @param model Model
   * @return ticket
   */
  @GetMapping(value={"/tickets"})
  public String displayTickets(Model model, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }
    String sessionId = session.getId();

    List<UserTicket> ticketList = new ArrayList<>();
    List<String> headers = Arrays.asList(
        EventConstants.HEADERS_NAME,
        EventConstants.HEADERS_EVENT_ID,
        EventConstants.TICKET_COUNT
      );

    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      int ticketCount = 0;
      ResultSet results = DBTicket.getMyTickets(con, userId);

      int temp = 0;
      if(results.next()) {
        temp = results.getInt(EventConstants.EVENT_ID);
      }
      while(results.next()) {
        int eventId = results.getInt(EventConstants.EVENT_ID);
        ticketCount++;

        if (temp != eventId) {
          String eventName = DBEvent.getEventName(con, temp);
          if (eventName == null) {
            logger.warn("Event doesn't exist");
            return "redirect:/error-400";
          }
          UserTicket ticket = new UserTicket(temp, eventName, ticketCount);
          ticketList.add(ticket);
          temp = eventId;
          ticketCount = 0;
        }
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute("headers", headers);
    model.addAttribute("tickets", ticketList);
    return "tickets";
  }

}

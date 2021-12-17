package cs601.project4.web.controller;

import static cs601.project4.web.WebUtilities.notifyFailedQuery;
import static cs601.project4.web.WebUtilities.validateLogin;

import cs601.project4.constant.EventConstants;
import cs601.project4.constant.NotificationConstants;
import cs601.project4.constant.TicketConstants;
import cs601.project4.constant.TransactionConstants;
import cs601.project4.constant.UserConstants;
import cs601.project4.database.DBEvent.EventSelectQuery;
import cs601.project4.database.DBManager;
import cs601.project4.database.DBSessionId;
import cs601.project4.database.DBTicket;
import cs601.project4.database.DBTransaction;
import cs601.project4.database.DBUser;
import cs601.project4.model.TicketTransferal;
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
  @GetMapping(value={"/tickets-cart/{eventId}"})
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
    return "tickets-cart";
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
      HttpServletRequest request,
      Model model) {

    String sessionId = validateLogin(request);

    if (numTickets < 1) {
      notifyFailedQuery(model, NotificationConstants.NOTIFY_MIN_TICKET);
      return "tickets-status";
    }

    try (Connection con = DBManager.getConnection()) {
      int eventUserId = EventSelectQuery.getUserIdByEventId(con, eventId);
      int availTicket = DBTicket.getTicketAvail(con, eventId);
      if (checkAvailTicket(numTickets, model, availTicket)) {
        return "tickets-status";
      }
      int userId = DBSessionId.getUserId(con, sessionId);
      if (eventUserId == userId) {
        notifyFailedQuery(model, NotificationConstants.NOTIFY_OWNER);
        return "tickets-status";
      }
      if (!DBTicket.buyTickets(con, userId, eventId, eventUserId, numTickets)){
        notifyFailedQuery(model, NotificationConstants.NOTIFY_FAIL);
        return "tickets-status";
      } else {
        int curAvailTicket = DBTicket.countTickets(con, eventUserId, eventId);
        if (curAvailTicket == availTicket || curAvailTicket == -1) {
          notifyFailedQuery(model, NotificationConstants.NOTIFY_FAIL);
          return "tickets-status";
        } else {
          DBTicket.updateTicketAvail(con, availTicket - numTickets, eventId);
          int purchasedSoFar = DBTicket.getTicketPurchased(con, eventId);
          if (purchasedSoFar == -1) {
            notifyFailedQuery(model, NotificationConstants.NOTIFY_FAIL);
            return "tickets-status";
          }
          DBTicket.updateTicketPurchased(con,purchasedSoFar + numTickets, eventId);
          DBTransaction.insertTransaction(con, eventId, eventUserId, userId, numTickets);
        }
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute(NotificationConstants.MSG, NotificationConstants.NOTIFY_SUCCESS);
    return "tickets-status";
  }

  /**
   * A helper class to check the number of available ticket
   *
   * @param numTickets  number of Tickets to be purchased
   * @param model       Model
   * @param availTicket number of available ticket
   * @return true if there is enough available ticket
   */
  private boolean checkAvailTicket(
      @RequestParam(EventConstants.NUM_TICKET) int numTickets,
      Model model, int availTicket) {
    if (availTicket == 0)  {
      notifyFailedQuery(model, NotificationConstants.NOTIFY_SOLD_OUT);
      return true;
    }
    if (availTicket - numTickets < 0) {
      notifyFailedQuery(model, NotificationConstants.NOTIFY_SPACE);
      return true;
    }
    return false;
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

    String sessionId = validateLogin(request);

    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      model.addAttribute(EventConstants.EVENT_ID, eventId);
      model.addAttribute(UserConstants.USER_ID, userId);
      model.addAttribute(EventConstants.NUM_TICKET, 1);
      model.addAttribute(UserConstants.EMAIL, "abc@gmail.com");
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "tickets-transferal-form";
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
      HttpServletRequest request,
      Model model) {

    String sessionId = validateLogin(request);

    if (numTickets < 1) {
      notifyFailedQuery(model, NotificationConstants.NOTIFY_MIN_TICKET);
      return "tickets-status";
    }

    try (Connection con = DBManager.getConnection()) {

      int userId = DBSessionId.getUserId(con, sessionId);
      int recipientId = DBUser.getUserId(con, email);
      int organizerId = EventSelectQuery.getUserIdByEventId(con, eventId);
      if (recipientId <= 0) {
        notifyFailedQuery(model, NotificationConstants.NOTIFY_EMAIL);
        return "tickets-status";
      }
      if (userId == recipientId) {
        notifyFailedQuery(model, NotificationConstants.NOTIFY_OTHER);
        return "tickets-status";
      }
      if (userId == organizerId) {
        notifyFailedQuery(model, NotificationConstants.NOTIFY_ORGANIZER);
        return "tickets-status";
      }

      int availTicket = DBTicket.getTicketAvail(con, eventId);

      if (checkAvailTicket(numTickets, model, availTicket)) {
        notifyFailedQuery(model, NotificationConstants.NOTIFY_SPACE);
        return "tickets-status";
      }

      if (!DBTicket.transferTickets(con, userId, recipientId, eventId, numTickets)){
        notifyFailedQuery(model, NotificationConstants.NOTIFY_TRANSFER_FAIL);
        return "tickets-status";
      }
      DBTransaction.insertTransaction(con, eventId, userId, recipientId, numTickets);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute(NotificationConstants.MSG,NotificationConstants.NOTIFY_TRANSFER_SUCCESS);
    return "tickets-status";
  }

  /**
   * Display all tickets with userId
   *
   * @param model Model
   * @return ticket
   */
  @GetMapping(value={"/tickets"})
  public String displayTickets(Model model, HttpServletRequest request) {

    String sessionId = validateLogin(request);

    List<UserTicket> ticketList = new ArrayList<>();
    List<String> headers = Arrays.asList(
        EventConstants.HEADERS_NAME,
        EventConstants.HEADERS_EVENT_ID,
        TransactionConstants.HEADERS_FROM,
        EventConstants.TICKET_COUNT
      );

    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      ResultSet results = DBTicket.getTicketsById(con, userId);

      int temp = 0;
      while(results.next()) {
        int eventId = results.getInt(EventConstants.EVENT_ID);
        if (temp != eventId) {
          int ticketCount = DBTicket.countTickets(con, userId, eventId);
          String eventName = EventSelectQuery.getEventName(con, eventId);
          if (eventName == null) {
            logger.warn("Event doesn't exist");
            return "redirect:/error-400";
          }
          int organizerId = EventSelectQuery.getUserIdByEventId(con, eventId);
          String organizer = DBUser.getUserName(con, organizerId);
          UserTicket ticket = new UserTicket(eventId, eventName, ticketCount, organizer);
          ticketList.add(ticket);
          temp = eventId;
        }
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute("headers", headers);
    model.addAttribute("tickets", ticketList);
    return "tickets";
  }

  /**
   * Display tickets that user have purchased from others
   *
   * @param model Model
   * @return ticket
   */
  @GetMapping(value={"/tickets-transferal"})
  public String getPurchasedTickets(Model model, HttpServletRequest request) {

    String sessionId = validateLogin(request);

    List<TicketTransferal> ticketList = new ArrayList<>();
    List<String> headers = Arrays.asList(
        TicketConstants.HEADERS_ID,
        EventConstants.HEADERS_NAME,
        EventConstants.HEADERS_EVENT_ID,
        TransactionConstants.HEADERS_FROM,
        EventConstants.TICKET_COUNT,
        TicketConstants.HEADERS_TRANSFER
    );

    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      ResultSet results = DBTicket.getPurchasedTicket(con, userId);

      while(results.next()) {
        TicketTransferal ticketTransferal = new TicketTransferal();
        ticketTransferal.setTicketId(results.getInt(TicketConstants.TICKET_ID));
        int eventId = results.getInt(EventConstants.EVENT_ID);
        ticketTransferal.setEventId(eventId);
        String eventName = EventSelectQuery.getEventName(con, eventId);
        if (eventName == null) {
          logger.warn("Event doesn't exist");
          return "redirect:/error-400";
        }
        ticketTransferal.setEventName(eventName);
        int organizerId = EventSelectQuery.getUserIdByEventId(con, eventId);
        ticketTransferal.setOrganizer(DBUser.getUserName(con, organizerId));
        int ticketCount = DBTicket.countTickets(con, userId, eventId);
        ticketTransferal.setTicketCount(ticketCount);
        ticketList.add(ticketTransferal);
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute("headers", headers);
    model.addAttribute("tickets", ticketList);
    return "tickets-transferal";
  }
}

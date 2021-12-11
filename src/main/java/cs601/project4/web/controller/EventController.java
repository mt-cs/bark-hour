package cs601.project4.web.controller;

import cs601.project4.constant.EventConstants;
import cs601.project4.constant.UserConstants;
import cs601.project4.database.DBEvent;
import cs601.project4.database.DBManager;
import cs601.project4.database.DBSessionId;
import cs601.project4.database.DBTicket;
import cs601.project4.model.Event;
import cs601.project4.model.Ticket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for event
 *
 * @author marisatania
 */
@Controller
public class EventController {
  private static final Logger logger = LoggerFactory.getLogger(EventController.class);

  /**
   * Display events
   *
   * @param model Model
   * @return login-error
   */
  @GetMapping(value={"/events"})
  public String displayEvents(Model model, HttpServletRequest request) {
    getAllEvents(model, request);
    return "home";
  }

  /**
   * A helper method to get all events
   *
   * @param model Model
   */
  public static void getAllEvents(Model model, HttpServletRequest request) {
    List<Event> events = new ArrayList<>();
    List<String> headers = Arrays.asList(
        EventConstants.HEADERS_NAME,
        EventConstants.HEADERS_ABOUT,
        EventConstants.HEADERS_LOCATION,
        EventConstants.HEADERS_CITY,
        EventConstants.HEADERS_START,
        EventConstants.HEADERS_END,
        EventConstants.HEADERS_INFO);

    String sessionId = request.getSession(true).getId();
    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      ResultSet results = DBEvent.selectAllEvent(con);
      while(results.next()) {
        Event event = new Event();
        event.setEventId(results.getInt(EventConstants.EVENT_ID));
        event.setEventName(results.getString(EventConstants.EVENT_NAME));
        event.setAbout(results.getString(EventConstants.ABOUT));
        event.setVenue(results.getString(EventConstants.VENUE));
        event.setCity(results.getString(EventConstants.CITY));
        event.setEventStart(results.getTimestamp(EventConstants.EVENT_START));
        event.setEventEnd(results.getTimestamp(EventConstants.EVENT_END));
        event.setUserId(userId);
        event.setNumTickets(results.getInt(EventConstants.NUM_TICKET));
        event.setNumTicketAvail(results.getInt(EventConstants.NUM_TICKET));
        event.setNumTicketPurchased(results.getInt(EventConstants.NUM_TICKET_PURCHASED));
        events.add(event);
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute("headers", headers);
    model.addAttribute("events", events);
  }

  /**
   * Handles create event post method
   *
   * @return event-status
   */
  @PostMapping(value={"/event-status"})
  public String createEventSubmit(
      @RequestParam(EventConstants.EVENT_NAME) String eventName,
      @RequestParam(EventConstants.ABOUT) String about,
      @RequestParam(EventConstants.VENUE) String venue,
      @RequestParam(EventConstants.ADDRESS) String address,
      @RequestParam(EventConstants.CITY) String city,
      @RequestParam(EventConstants.STATE) String state,
      @RequestParam(EventConstants.COUNTRY) String country,
      @RequestParam(EventConstants.ZIP) int zip,
      @RequestParam(EventConstants.EVENT_START) String start,
      @RequestParam(EventConstants.EVENT_END) String end,
      @RequestParam(EventConstants.NUM_TICKET) int numTickets,
      HttpServletRequest request) {

    if (numTickets < 1) {
      logger.warn("Number of tickets has to be at least 1");
      return "redirect:/error-400";
    }

    String sessionId = request.getSession(true).getId();
    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      if (DBEvent.checkEventExist(con, eventName)) {
        logger.info("Event already exists.");
        return "redirect:/error-400";
      } else {
        int eventId = DBEvent.createEvent(
            con, userId, eventName, venue, address, city, state,
            country, zip, about, start, end, numTickets);

        if(!DBTicket.insertTickets(con, new Ticket(userId, eventId), numTickets)) {
          return "redirect:/error";
        }
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "event-status";
  }

  /**
   * Handles create event get method
   *
   * @return new-event
   */
  @GetMapping(value={"/new-event"})
  public String createEventForm() {
    return "new-event";
  }

  /**
   * Handles even that just been created
   */
  @GetMapping(value={"/event-status"})
  public String getEventStatus() {
    return "event-status";
  }

  /**
   * Get detail on one event
   *
   * @param model   Model model
   * @param eventId Event ID int
   * @return event.html
   */
  @GetMapping(value={"/event/{eventId}"})
  public String getEvent(Model model, @PathVariable int eventId) {
    try (Connection con = DBManager.getConnection()) {
      ResultSet results = DBEvent.selectEvent(con, eventId);
      while(results.next()) {
        model.addAttribute(EventConstants.EVENT_ID, results.getString(EventConstants.EVENT_ID));
        model.addAttribute(EventConstants.EVENT_NAME, results.getString(EventConstants.EVENT_NAME));
        model.addAttribute(EventConstants.ABOUT, results.getString(EventConstants.ABOUT));
        model.addAttribute(EventConstants.VENUE, results.getString(EventConstants.VENUE));
        model.addAttribute(EventConstants.ADDRESS, results.getString(EventConstants.ADDRESS));
        model.addAttribute(EventConstants.CITY, results.getString(EventConstants.CITY));
        model.addAttribute(EventConstants.STATE, results.getString(EventConstants.STATE));
        model.addAttribute(EventConstants.COUNTRY, results.getString(EventConstants.COUNTRY));
        model.addAttribute(EventConstants.ZIP, results.getString(EventConstants.ZIP));
        model.addAttribute(EventConstants.EVENT_START, results.getString(EventConstants.EVENT_START));
        model.addAttribute(EventConstants.EVENT_END, results.getString(EventConstants.EVENT_END));
        model.addAttribute(EventConstants.NUM_TICKET, results.getString(EventConstants.NUM_TICKET));
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "event";
  }

  @GetMapping("/event-update")
  public String getEventUpdateForm() {
    return "event-update";
  }

  /**
   * Handles update event form
   *
   * @return event-status
   */
  @PostMapping(value={"/event-update"})
  public String updateEvent(
      @RequestParam(EventConstants.EVENT_NAME) String eventName,
      @RequestParam(EventConstants.ABOUT) String about,
      @RequestParam(EventConstants.VENUE) String venue,
      @RequestParam(EventConstants.ADDRESS) String address,
      @RequestParam(EventConstants.CITY) String city,
      @RequestParam(EventConstants.STATE) String state,
      @RequestParam(EventConstants.COUNTRY) String country,
      @RequestParam(EventConstants.ZIP) int zip,
      @RequestParam(EventConstants.EVENT_START) String start,
      @RequestParam(EventConstants.EVENT_END) String end,
      @RequestParam(EventConstants.NUM_TICKET) int numTickets,
      HttpServletRequest request) {

    if (numTickets < 1) {
      logger.warn("Number of tickets has to be at least 1");
      return "redirect:/error-400";
    }

    String sessionId = request.getSession(true).getId();
    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);

      DBEvent.updateEvent(
          con, 1, userId, eventName, venue, address, city, state,
          country, zip, about, start, end, numTickets);


//      if(!DBTicket.insertTickets(con, new Ticket(userId, eventId), numTickets)) {
//        return "redirect:/error";
//      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "event-update";
  }
  /**
   * Handles update event form
   *
   * @return event-status
   */
  @GetMapping(value={"/update-event/{eventId}"})
  public String getUpdateEventForm(Model model, @PathVariable int eventId) {
    try (Connection con = DBManager.getConnection()) {
      ResultSet results = DBEvent.selectEvent(con, eventId);
      while(results.next()) {
        model.addAttribute(EventConstants.EVENT_ID, results.getString(EventConstants.EVENT_ID));
        model.addAttribute(EventConstants.EVENT_NAME, results.getString(EventConstants.EVENT_NAME));
        model.addAttribute(EventConstants.ABOUT, results.getString(EventConstants.ABOUT));
        model.addAttribute(EventConstants.VENUE, results.getString(EventConstants.VENUE));
        model.addAttribute(EventConstants.ADDRESS, results.getString(EventConstants.ADDRESS));
        model.addAttribute(EventConstants.CITY, results.getString(EventConstants.CITY));
        model.addAttribute(EventConstants.STATE, results.getString(EventConstants.STATE));
        model.addAttribute(EventConstants.COUNTRY, results.getString(EventConstants.COUNTRY));
        model.addAttribute(EventConstants.ZIP, results.getString(EventConstants.ZIP));
        model.addAttribute(EventConstants.EVENT_START, results.getString(EventConstants.EVENT_START));
        model.addAttribute(EventConstants.EVENT_END, results.getString(EventConstants.EVENT_END));
        model.addAttribute(EventConstants.NUM_TICKET, results.getString(EventConstants.NUM_TICKET));
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "update-event";
  }

}

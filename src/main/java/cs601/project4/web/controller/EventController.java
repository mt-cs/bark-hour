package cs601.project4.web.controller;

import cs601.project4.constant.EventConstants;
import cs601.project4.database.DBEvent;
import cs601.project4.database.DBManager;
import cs601.project4.database.DBSessionId;
import cs601.project4.model.Event;
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
  private final Logger logger = LoggerFactory.getLogger(EventController.class);

  /**
   * Display events
   *
   * @param model Model
   * @return login-error
   */
  @GetMapping(value={"/events"})
  public String displayEvents(Model model) {
    int eventID = 0;
    List<Event> events = new ArrayList<>();
    Event event = null;
//    List<List<String>> events = new ArrayList<>();
    List<String> headers =
        Arrays.asList("Event Name", "About", "Location", "Start", "End");

    try (Connection con = DBManager.getConnection()) {
      ResultSet results = DBEvent.selectAllEvent(con);

      while(results.next()) {
        String location = results.getString(EventConstants.VENUE)
            + EventConstants.NEW_LINE
            + results.getString(EventConstants.CITY)
            + EventConstants.COMMA
            + results.getString(EventConstants.STATE)
            + EventConstants.COMMA
            + results.getString(EventConstants.ZIP)
            + EventConstants.NEW_LINE
            + results.getString(EventConstants.COUNTRY);
        event = new Event(++eventID,
            results.getString(EventConstants.EVENT_NAME),
            results.getString(EventConstants.ABOUT),
            location,
            results.getTimestamp(EventConstants.EVENT_START),
            results.getTimestamp(EventConstants.EVENT_END),
            1,
            results.getInt(EventConstants.NUM_TICKET),
            results.getInt(EventConstants.NUM_TICKET),
            0);
        logger.debug(event.toString());
        events.add(event);
//        events.add(Arrays.asList(
//            results.getString(EventConstants.EVENT_NAME),
//            results.getString(EventConstants.ABOUT),
//            results.getString(EventConstants.VENUE)
//                + EventConstants.NEW_LINE
//                + results.getString(EventConstants.CITY)
//                + EventConstants.COMMA
//                + results.getString(EventConstants.STATE)
//                + EventConstants.COMMA
//                + results.getString(EventConstants.COUNTRY)
//                + EventConstants.NEW_LINE
//                + results.getString(EventConstants.ZIP),
//                results.getString(EventConstants.EVENT_START),
//            results.getString(EventConstants.EVENT_END)));
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
//    assert event != null;
//    model.addAttribute("event", event.getEventName());
    model.addAttribute("headers", headers);
    model.addAttribute("events", events);
    return "events";
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

    String sessionId = request.getSession(true).getId();
    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      if (!DBEvent.checkEventExist(con, eventName)) {
        logger.info("Event already exists.");
      }
      DBEvent.createEvent(
          con, userId, eventName, venue, address, city, state,
          country, zip, about, start, end, numTickets);
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

  @GetMapping(value={"/event/{eventId}"})
  public String getEvent(Model model, @PathVariable int eventId) {
    try (Connection con = DBManager.getConnection()) {
      ResultSet results = DBEvent.selectEvent(con, eventId);
      while(results.next()) {
        model.addAttribute(EventConstants.EVENT_NAME, results.getString(EventConstants.EVENT_NAME));
        model.addAttribute(EventConstants.ABOUT, results.getString(EventConstants.ABOUT));
        model.addAttribute(EventConstants.VENUE, results.getString(EventConstants.VENUE));
        model.addAttribute(EventConstants.CITY, results.getString(EventConstants.CITY));
        model.addAttribute(EventConstants.STATE, results.getString(EventConstants.STATE));
        model.addAttribute(EventConstants.EVENT_START, results.getString(EventConstants.EVENT_END));
        model.addAttribute(EventConstants.NUM_TICKET_AVAIL, results.getString(EventConstants.NUM_TICKET_AVAIL));
        model.addAttribute(EventConstants.NUM_TICKET, results.getString(EventConstants.NUM_TICKET));
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "event";
  }

//  @PostMapping(value={"/event"})
//  public String postEvent(@RequestParam(EventConstants.EVENT_NAME) String eventName, Model model) {
//    try (Connection con = DBManager.getConnection()) {
//      ResultSet results = DBEvent.selectEvent(con,eventName);
//      while(results.next()) {
//        model.addAttribute(EventConstants.EVENT_NAME, results.getString(EventConstants.EVENT_NAME));
//        model.addAttribute(EventConstants.ABOUT, results.getString(EventConstants.ABOUT));
//        model.addAttribute(EventConstants.VENUE, results.getString(EventConstants.VENUE));
//        model.addAttribute(EventConstants.CITY, results.getString(EventConstants.CITY));
//        model.addAttribute(EventConstants.STATE, results.getString(EventConstants.STATE));
//        model.addAttribute(EventConstants.EVENT_START, results.getString(EventConstants.EVENT_START));
//        model.addAttribute(EventConstants.EVENT_END, results.getString(EventConstants.EVENT_END));
//        model.addAttribute(EventConstants.NUM_TICKET, results.getString(EventConstants.NUM_TICKET));
//      }
//    } catch (SQLException sqlException) {
//      logger.error(sqlException.getMessage());
//    }
//    return "event";
//  }
}

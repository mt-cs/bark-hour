package cs601.project4.web.controller;

import static cs601.project4.web.Util.notifyFailedQuery;

import cs601.project4.constant.EventConstants;
import cs601.project4.constant.NotificationConstants;
import cs601.project4.database.DBEvent.EventInsertQuery;
import cs601.project4.database.DBEvent.EventSelectQuery;
import cs601.project4.database.DBEvent.EventUpdateQuery;
import cs601.project4.database.DBManager;
import cs601.project4.database.DBSessionId;
import cs601.project4.database.DBTicket;
import cs601.project4.model.Event;
import cs601.project4.model.Ticket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
   * @return home
   */
  @GetMapping(value = {"/events"})
  public String displayEvents(Model model, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }

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
      ResultSet results = EventSelectQuery.selectAllEvent(con);
      setEvent(events, userId, results);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute("headers", headers);
    model.addAttribute("events", events);
  }

  /**
   * A helper method to add event object to list
   *
   * @param events  List of events
   * @param userId  User ID
   * @param results query results
   * @throws SQLException
   */
  private static void setEvent(List<Event> events, int userId, ResultSet results)
      throws SQLException {
    while (results.next()) {
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
      event.setNumTicketAvail(results.getInt(EventConstants.NUM_TICKET_AVAIL));
      event.setNumTicketPurchased(results.getInt(EventConstants.NUM_TICKET_PURCHASED));
      events.add(event);
    }
  }

  /**
   * Handles create event post method
   *
   * @return event-status
   */
  @PostMapping(value = {"/event-status"})
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
      HttpServletRequest request,
      Model model) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      return "event-status";
    }
    String sessionId = session.getId();

    if (numTickets < 1) {
      notifyFailedQuery(model, NotificationConstants.NOTIFY_MIN_TICKET);
      return "event-status";
    }

    if (validateInputDate(start, end, model)) {
      return "event-status";
    }

    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      if (EventSelectQuery.checkEventExist(con, eventName)) {
        notifyFailedQuery(model, NotificationConstants.NOTIFY_EVENT_EXIST);
        return "event-status";
      } else {
        int eventId = EventInsertQuery.createEvent(
            con, userId, eventName, venue, address, city, state,
            country, zip, about, start, end, numTickets);

        if (!DBTicket.insertTickets(con, new Ticket(userId, eventId), numTickets)) {
          notifyFailedQuery(model, NotificationConstants.NOTIFY_EVENT_FAIL);
          return "event-status";
        }
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute(NotificationConstants.MSG, NotificationConstants.NOTIFY_EVENT_SUCCESS);
    return "event-status";
  }

  /**
   * Handles create event get method
   *
   * @return new-event
   */
  @GetMapping(value = {"/new-event"})
  public String createEventForm(Model model, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }
    model.addAttribute(EventConstants.EVENT_START, LocalDateTime.now());
    model.addAttribute(EventConstants.EVENT_END, LocalDateTime.now());
    model.addAttribute(EventConstants.CITY, EventConstants.SF);
    model.addAttribute(EventConstants.STATE, EventConstants.CA);
    model.addAttribute(EventConstants.COUNTRY, EventConstants.USA);
    return "new-event";
  }

  /**
   * Handles even that just been created
   */
  @GetMapping(value = {"/event-status"})
  public String getEventStatus(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }
    return "event-status";
  }

  /**
   * Get detail on one event
   *
   * @param model   Model model
   * @param eventId Event ID int
   * @return event.html
   */
  @GetMapping(value = {"/event/{eventId}"})
  public String getEvent(Model model, @PathVariable int eventId, HttpServletRequest request) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }

    try (Connection con = DBManager.getConnection()) {
      ResultSet results = EventSelectQuery.selectEvent(con, eventId);
      while (results.next()) {
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

  /**
   * Handles update event form
   *
   * @return event-status
   */
  @PostMapping(value = {"/event-update-status"})
  public String updateEvent(
      @RequestParam(EventConstants.EVENT_ID) int eventId,
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
      Model model,
      HttpServletRequest request) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }
    String sessionId = session.getId();

    if (numTickets < 1) {
      notifyFailedQuery(model, NotificationConstants.NOTIFY_MIN_TICKET);
      return "redirect:/error-400";
    }

    if (validateInputDate(start, end, model)) {
      return "event-status";
    }

    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      int organizerId = EventSelectQuery.getUserIdByEventId(con, eventId);

      if (organizerId != userId) {
        notifyFailedQuery(model, NotificationConstants.NOTIFY_NOT_ORGANIZER);
        return "event-update-status";
      }
      EventUpdateQuery.updateEvent(
          con, eventId, userId, eventName, venue, address, city, state,
          country, zip, about, start, end, numTickets);

    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute(NotificationConstants.MSG, NotificationConstants.NOTIFY_EVENT_UPDATE_SUCCESS);
    return "event-update-status";
  }

  /**
   * A helper class to validate start date and end date input
   *
   * @param start Start Date
   * @param end   End Date
   * @param model Model
   * @return true if date is valid
   */
  private boolean validateInputDate(
      @RequestParam(EventConstants.EVENT_START) String start,
      @RequestParam(EventConstants.EVENT_END) String end,
      Model model) {
    Date startDate = new Date(), endDate = new Date(), today = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
    try {
      startDate = formatter.parse(start);
      endDate = formatter.parse(end);
      today = formatter.parse(String.valueOf(LocalDate.now(ZoneId.systemDefault())));
    } catch (ParseException e) {
      logger.error(e.getMessage());
    }

    if (startDate.before(today) || endDate.before(today)) {
      notifyFailedQuery(model, NotificationConstants.NOTIFY_DATE_PAST);
      return true;
    }

    if (startDate.after(endDate)) {
      notifyFailedQuery(model, NotificationConstants.NOTIFY_END_DATE);
      return true;
    }
    return false;
  }

  /**
   * Handles update event form
   *
   * @return event-status
   */
  @GetMapping(value = {"/update-event/{eventId}"})
  public String getUpdateEventForm(Model model, @PathVariable int eventId,
      HttpServletRequest request) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }

    try (Connection con = DBManager.getConnection()) {
      ResultSet results = EventSelectQuery.selectEvent(con, eventId);
      while (results.next()) {
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

  /**
   * Get detail on one event
   *
   * @param model Model model
   * @return events.html
   */
  @GetMapping(value = {"/users-events"})
  public String getMyEvents(Model model, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }

    String sessionId = session.getId();
    List<Event> events = new ArrayList<>();
    List<String> headers = Arrays.asList(
        EventConstants.HEADERS_EVENT_ID,
        EventConstants.HEADERS_NAME,
        EventConstants.HEADERS_ABOUT,
        EventConstants.HEADERS_LOCATION,
        EventConstants.HEADERS_CITY,
        EventConstants.HEADERS_START,
        EventConstants.HEADERS_END,
        EventConstants.HEADERS_CAPACITY,
        EventConstants.AVAIL,
        EventConstants.SOLD,
        EventConstants.HEADERS_UPDATE);

    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      ResultSet results = EventSelectQuery.getMyEvents(con, userId);

      setEvent(events, userId, results);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute("headers", headers);
    model.addAttribute("events", events);
    return "users-events";
  }
}

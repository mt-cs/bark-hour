package cs601.project4.web;

import cs601.project4.JDBC.DatabaseManager;
import cs601.project4.service.EventService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
//  @Autowired
//  private EventService eventService;

  /**
   * Display events
   *
   * @param request HttpServletRequest
   * @return        login-error
   */
  @GetMapping(value={"/events"})
  public String displayEvents(HttpServletRequest request) {
    return "events";
  }


  /**
   * Handles create event post method
   */
  @PostMapping(value={"/event-status"})
  public String createEventSubmit(
      @RequestParam("event_name") String eventName,
      @RequestParam("about") String about,
      @RequestParam("location") String location,
      @RequestParam("event_date") String date,
      @RequestParam("event_time") String time,
      @RequestParam("num_tickets") int numTickets,
      HttpServletRequest request) {
    // Retrieve the ID of this session
    String sessionId = request.getSession(true).getId();
    try (Connection con = DatabaseManager.getConnection()) {
      int userId = DatabaseManager.getUserId(con, sessionId);
//      Date sqlDate = java.sql.Date.valueOf(date);
//      Time sqlTime = java.sql.Time.valueOf(time);
      DatabaseManager.createEvent(con, userId, eventName, about, location, numTickets);
      //DatabaseManager.createEvent(con, userId, eventName, about, location, sqlDate, sqlTime, numTickets);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "event-status";
  }

  /**
   * Handles create event get method
   */
  @GetMapping(value={"/new-event"})
  public String createEventFormForm() {
    return "new-event";
  }

  /**
   * Handles even that just been created
   */
  @GetMapping(value={"/event-status"})
  public String getEventStatus() {
    return "event-status";
  }
}

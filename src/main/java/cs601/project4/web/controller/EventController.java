package cs601.project4.web.controller;

import cs601.project4.database.DBManager;
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
   * @return        login-error
   */
  @GetMapping(value={"/events"})
  public String displayEvents(Model model) {
    List<String> headers = Arrays.asList("Event Name", "About", "Location", "Date", "Time");
    List<List<String>> events = new ArrayList<>();

    try (Connection con = DBManager.getConnection()) {
      ResultSet results = DBManager.selectEvent(con);

      while(results.next()) {
        events.add(Arrays.asList(
            results.getString("event_name"),
            results.getString("about"),
            results.getString("location"),
            results.getString("event_date"),
            results.getString("event_time")));

//        events.add(Map.of("event_name", results.getString("event_name"),
//            "about", results.getString("about"),
//            "location", results.getString("location"),
//            "date", results.getString("event_date"),
//            "time", results.getString("event_time")));
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute("headers", headers);
    model.addAttribute("rows", events);
    return "events";
  }

  //TODO: Add start time end time
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
    try (Connection con = DBManager.getConnection()) {
      int userId = DBManager.getUserId(con, sessionId);
      if (!DBManager.createEvent(con, userId, eventName, about, location, date, time, numTickets)) {
        logger.info("Event already exists.");
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "event-status";
  }

  /**
   * Handles create event get method
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

  @GetMapping(value={"/event"})
  public String getEvent(Model model, String eventName) {
    try (Connection con = DBManager.getConnection()) {
      ResultSet results = DBManager.selectEvent(con,eventName);
      while(results.next()) {
        model.addAttribute("event_name", results.getString("event_name"));
        model.addAttribute("about", results.getString("about"));
        model.addAttribute("location", results.getString("location"));
        model.addAttribute("event_date", results.getString("event_date"));
        model.addAttribute("event_time", results.getString("event_time"));
        model.addAttribute("num_ticket_avail", results.getString("num_ticket_avail"));
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    return "event";
  }

}

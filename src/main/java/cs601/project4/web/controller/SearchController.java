package cs601.project4.web.controller;

import cs601.project4.constant.EventConstants;
import cs601.project4.database.DBEvent.EventSearchQuery;
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
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for event
 *
 * @author marisatania
 */
@Controller
public class SearchController {
  private final Logger logger = LoggerFactory.getLogger(SearchController.class);

  /**
   * Display events partial searcj by Name
   *
   * @param model   Model
   * @param request HTTP Servlet Request
   * @param query   Result parameter query
   * @return results-name
   */
  @GetMapping(value = {"/results-name"})
  public String searchByName(
      Model model,
      HttpServletRequest request,
      @RequestParam(value="query",required=false) String query) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }

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
      ResultSet results = EventSearchQuery.searchEventByName(con, query);
      addEventToList(events, userId, results);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute("headers", headers);
    model.addAttribute("events", events);
    return "results-name";
  }

  /**
   * Display events partial searcj by Name
   *
   * @param model   Model
   * @param request HTTP Servlet Request
   * @param query   Result parameter query
   * @return results-name
   */
  @GetMapping(value = {"/results-today"})
  public String searchByCurrentDate(
      Model model,
      HttpServletRequest request,
      @RequestParam(value="query",required=false) String query) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }

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
      ResultSet results = EventSearchQuery.searchByCurrentDate(con, query);
      addEventToList(events, userId, results);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute("headers", headers);
    model.addAttribute("events", events);
    return "results-name";

  }

  private void addEventToList(List<Event> events, int userId, ResultSet results)
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
      event.setNumTicketAvail(results.getInt(EventConstants.NUM_TICKET));
      event.setNumTicketPurchased(results.getInt(EventConstants.NUM_TICKET_PURCHASED));
      events.add(event);
    }
  }

}

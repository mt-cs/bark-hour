package cs601.project4.web.controller;

import static cs601.project4.web.WebUtilities.validateLogin;

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
   * Display events partial search by Name
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

    validateLogin(request);

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
    return "results";
  }

  /**
   * Display events partial search by Name
   *
   * @param model   Model
   * @param request HTTP Servlet Request
   * @param query   Result parameter query
   * @return results-name
   */
  @GetMapping(value = {"/results-location"})
  public String searchByLocation(
      Model model,
      HttpServletRequest request,
      @RequestParam(value="query",required=false) String query) {

    validateLogin(request);

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
      ResultSet results = EventSearchQuery.searchEventByLocation(con, query);
      addEventToList(events, userId, results);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute("headers", headers);
    model.addAttribute("events", events);
    return "results";
  }

  /**
   * Display events partial search by Today's date
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

    validateLogin(request);

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
    return "results";
  }


  /**
   * Display events partial search by This Month's date
   *
   * @param model   Model
   * @param request HTTP Servlet Request
   * @param query   Result parameter query
   * @return results-name
   */
  @GetMapping(value = {"/results-month"})
  public String searchByCurrentMonth(
      Model model,
      HttpServletRequest request,
      @RequestParam(value="query",required=false) String query) {

    validateLogin(request);

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
      ResultSet results = EventSearchQuery.searchByCurrentMonth(con, query);
      addEventToList(events, userId, results);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute("headers", headers);
    model.addAttribute("events", events);
    return "results";
  }

  /**
   * Display events partial search by This Month's date
   *
   * @param model   Model
   * @param request HTTP Servlet Request
   * @param query   Result parameter query
   * @return results-name
   */
  @GetMapping(value = {"/results-year"})
  public String searchByCurrentYear(
      Model model,
      HttpServletRequest request,
      @RequestParam(value="query",required=false) String query) {

    validateLogin(request);

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
      ResultSet results = EventSearchQuery.searchByCurrentYear(con, query);
      addEventToList(events, userId, results);
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute("headers", headers);
    model.addAttribute("events", events);
    return "results";
  }

  /**
   * A helper method to add event to events list
   *
   * @param events  event list
   * @param userId  user ID
   * @param results ResultSet
   * @throws SQLException error database
   */
  private void addEventToList(List<Event> events, int userId, ResultSet results)
      throws SQLException {
    while (results.next()) {
      Event event = EventController.createNewEvent(userId, results);
      event.setNumTicketAvail(results.getInt(EventConstants.NUM_TICKET));
      event.setNumTicketPurchased(results.getInt(EventConstants.NUM_TICKET_PURCHASED));
      events.add(event);
    }
  }

}

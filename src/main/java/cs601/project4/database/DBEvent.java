package cs601.project4.database;

import cs601.project4.constant.EventConstants;
import cs601.project4.constant.UserConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Query statements for the events table
 *
 * @author marisatania
 */
public class DBEvent {
  private static final Logger logger = LoggerFactory.getLogger(DBEvent.class);

  /**
   * Retrieve all event in the events table
   *
   * @param con Connection
   * @throws SQLException database access error
   */
  public static ResultSet selectAllEvent(Connection con) throws SQLException{
    String selectAllContactsSql = "SELECT * FROM events;";
    PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
    ResultSet results = selectAllContactsStmt.executeQuery();
    return results;
  }

  /**
   * Perform select events by name
   *
   * @param con Connection
   * @throws SQLException database access error
   */
  public static ResultSet selectEvent(Connection con, int eventId)
      throws SQLException {
    String selectUserSql = "SELECT * FROM events WHERE event_id = ?;";
    PreparedStatement selectUserStmt = con.prepareStatement(selectUserSql);
    selectUserStmt.setInt(1, eventId);
    ResultSet results = selectUserStmt.executeQuery();
    return results;
  }

  /**
   * Get all events by userId
   *
   * @param con    Connection
   * @param userId int user ID
   * @return results
   * @throws SQLException database access error
   */
  public static ResultSet getMyEvents(Connection con, int userId)
      throws SQLException {
    String selectUserSql = "SELECT * FROM events WHERE userid = ?;";
    PreparedStatement selectUserStmt = con.prepareStatement(selectUserSql);
    selectUserStmt.setInt(1, userId);
    ResultSet results = selectUserStmt.executeQuery();
    return results;
  }

  /**
   * Check if event is already in the database
   *
   * @param con       Connection
   * @param eventID   Event ID
   * @return true if event doesn't exist, false otherwise
   */
  public static Boolean checkEventExist(Connection con, String eventID)
      throws SQLException {
    String checkEventSql = "SELECT * FROM events WHERE event_id= ?";
    PreparedStatement insertUserStmt =
        con.prepareStatement(checkEventSql, Statement.RETURN_GENERATED_KEYS);
    insertUserStmt.setString(1, eventID);
    ResultSet rs = insertUserStmt.executeQuery();
    return rs.next();
  }

  /**
   * Performs SQL insert user to users table
   * if SQL query is successful, return User object with userid
   * otherwise return null
   *
   * @param con        Connection
   * @param userId     String user ID
   * @param eventName  String event name
   * @param venue      String event venue
   * @param address    String event address
   * @param city       String event city
   * @param state      String event state
   * @param country    String event country
   * @param zip        String event zip
   * @param about      String event about
   * @param start      String event start date
   * @param end        String event end date
   * @param numTickets int Number of tickets
   * @throws SQLException on database errors
   * @return true if successful
   */
  public static int createEvent(
      Connection con, int userId, String eventName, String venue,
      String address, String city, String state, String country, int zip,
      String about, String start, String end, int numTickets)
      throws SQLException {

    String insertEventSql = "INSERT INTO events "
        + "(userid, event_name, venue, address, city, state, "
        + "country, zip, about, event_start, event_end, num_ticket) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    PreparedStatement insertEventStmt =
        con.prepareStatement(insertEventSql, Statement.RETURN_GENERATED_KEYS);
    insertEventStmt.setInt(1, userId);
    insertEventStmt.setString(2, eventName);
    insertEventStmt.setString(3, venue);
    insertEventStmt.setString(4, address);
    insertEventStmt.setString(5, city);
    insertEventStmt.setString(6, state);
    insertEventStmt.setString(7, country);
    insertEventStmt.setInt(8, zip);
    insertEventStmt.setString(9, about);
    insertEventStmt.setString(10, start);
    insertEventStmt.setString(11, end);
    insertEventStmt.setInt(12, numTickets);
    insertEventStmt.executeUpdate();

    ResultSet result = insertEventStmt.getGeneratedKeys();
    if(result.next()) {
      return result.getInt(1);
    }
    logger.warn("Insert event failed");
    return -1;
  }

  /**
   * Update Event
   *
   * @param con        Connection
   * @param userId     String user ID
   * @param eventName  String event name
   * @param venue      String event venue
   * @param address    String event address
   * @param city       String event city
   * @param state      String event state
   * @param country    String event country
   * @param zip        String event zip
   * @param about      String event about
   * @param start      String event start date
   * @param end        String event end date
   * @param numTickets int Number of tickets
   * @throws SQLException on database errors
   */
  public static void updateEvent(
      Connection con, int eventId, int userId, String eventName, String venue,
      String address, String city, String state, String country, int zip,
      String about, String start, String end, int numTickets)
      throws SQLException {
    String updateEventSql = "UPDATE events "
        + "SET userid = ?, event_name = ?, venue = ?,"
        + "address = ?, city = ?, state = ?, country = ?, zip = ?,"
        + "about = ?, event_start = ?, event_end = ?, num_ticket = ?"
        + "WHERE eventId = ?";
    PreparedStatement updateEventStmt =
        con.prepareStatement(updateEventSql, Statement.RETURN_GENERATED_KEYS);
    updateEventStmt.setInt(1, userId);
    updateEventStmt.setString(2, eventName);
    updateEventStmt.setString(3, venue);
    updateEventStmt.setString(4, address);
    updateEventStmt.setString(5, city);
    updateEventStmt.setString(6, state);
    updateEventStmt.setString(7, country);
    updateEventStmt.setInt(8, zip);
    updateEventStmt.setString(9, about);
    updateEventStmt.setString(10, start);
    updateEventStmt.setString(11, end);
    updateEventStmt.setInt(12, numTickets);
    updateEventStmt.setInt(13, eventId);
    updateEventStmt.executeUpdate();
  }
}

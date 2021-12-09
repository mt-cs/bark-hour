package cs601.project4.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Query statements for the events table
 *
 * @author marisatania
 */
public class DBEvent {

  /**
   * Performs SQL insert user to users table
   * if SQL query is successful, return User object with userid
   * otherwise return null
   *
   * @return true if successful
   */
  public static boolean createEvent(Connection con, int userId, String eventName, String about, String location, String start, String end, int numTickets) throws SQLException {
    //public static boolean createEvent(Connection con, int userId, String eventName, String about, String location, Date date, Time time, int numTickets) throws SQLException{
    String insertEventSql = "INSERT IGNORE INTO events (userid, event_name, location, about,  event_start, event_end, num_ticket) VALUES (?, ?, ?, ?, ?, ?, ?)";
//    String insertEventSql = "INSERT IGNORE INTO events (userid, event_name, location, about, event_date, event_time, num_tickets_available) VALUES (?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement insertUserStmt = con.prepareStatement(insertEventSql, Statement.RETURN_GENERATED_KEYS);
    insertUserStmt.setInt(1, userId);
    insertUserStmt.setString(2, eventName);
    insertUserStmt.setString(3, location);
    insertUserStmt.setString(4, about);
    insertUserStmt.setString(5, start);
    insertUserStmt.setString(6, end);
    insertUserStmt.setInt(7, numTickets);
    insertUserStmt.executeUpdate();
    return true;
  }

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
  public static ResultSet selectEvent(Connection con, String eventName) throws SQLException {
    String selectUserSql = "SELECT * FROM events WHERE event_name = ?;";
    PreparedStatement selectUserStmt = con.prepareStatement(selectUserSql);
    selectUserStmt.setString(1, eventName);
    ResultSet results = selectUserStmt.executeQuery();
    return results;
  }

  /**
   * Check if event is already in the database
   *
   * @param con       Connection
   * @param eventName Event Name
   * @return true if event doesn't exist, false otherwise
   */
  public static Boolean checkEventExist(Connection con, String eventName) throws SQLException {
    String checkEventSql = "SELECT * FROM events WHERE event_name = ?";
    PreparedStatement insertUserStmt = con.prepareStatement(
        checkEventSql,
        Statement.RETURN_GENERATED_KEYS);
    insertUserStmt.setString(1, eventName);
    ResultSet rs = insertUserStmt.executeQuery();
    return !rs.next();
  }
}

package cs601.project4.database;

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

    PreparedStatement insertUserStmt =
        con.prepareStatement(insertEventSql, Statement.RETURN_GENERATED_KEYS);
    insertUserStmt.setInt(1, userId);
    insertUserStmt.setString(2, eventName);
    insertUserStmt.setString(3, venue);
    insertUserStmt.setString(4, address);
    insertUserStmt.setString(5, city);
    insertUserStmt.setString(6, state);
    insertUserStmt.setString(7, country);
    insertUserStmt.setInt(8, zip);
    insertUserStmt.setString(9, about);
    insertUserStmt.setString(10, start);
    insertUserStmt.setString(11, end);
    insertUserStmt.setInt(12, numTickets);
    insertUserStmt.executeUpdate();

    ResultSet result = insertUserStmt.getGeneratedKeys();
    if(result.next()) {
      return result.getInt(1);
    }
    logger.warn("Insert event failed");
    return -1;
  }
}

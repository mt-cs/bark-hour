package cs601.project4.database.DBEvent;

import cs601.project4.constant.EventConstants;
import cs601.project4.constant.UserConstants;
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
public class EventSelectQuery {

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
   * Get all userId by eventId
   *
   * @param con     Connection
   * @param eventId int event ID
   * @return userId
   * @throws SQLException database access error
   */
  public static int getUserIdByEventId(Connection con, int eventId)
      throws SQLException {
    String selectUserSql = "SELECT userid FROM events WHERE event_id = ?;";
    PreparedStatement selectUserStmt = con.prepareStatement(selectUserSql);
    selectUserStmt.setInt(1, eventId);
    ResultSet results = selectUserStmt.executeQuery();
    if(results.next()) {
      return results.getInt(UserConstants.USER_ID);
    }
    return -1;
  }

  /**
   * Get event name by eventId
   *
   * @param con     Connection
   * @param eventId int event ID
   * @return event name
   * @throws SQLException database access error
   */
  public static String getEventName(Connection con, int eventId)
      throws SQLException {
    String selectUserSql = "SELECT event_name FROM events WHERE event_id = ?;";
    PreparedStatement selectUserStmt = con.prepareStatement(selectUserSql);
    selectUserStmt.setInt(1, eventId);
    ResultSet results = selectUserStmt.executeQuery();
    if(results.next()) {
      return results.getString(EventConstants.EVENT_NAME);
    }
    return null;
  }

  /**
   * Get event name by eventId
   *
   * @param con       Connection
   * @param eventName String eventName
   * @param userId    int userId
   * @return event name
   * @throws SQLException database access error
   */
  public static int getEventId(Connection con, String eventName, int userId)
      throws SQLException {
    String selectUserSql = "SELECT event_id FROM events WHERE event_name = ? AND userid = ?;";
    PreparedStatement selectUserStmt = con.prepareStatement(selectUserSql);
    selectUserStmt.setString(1, eventName);
    selectUserStmt.setInt(2, userId);
    ResultSet results = selectUserStmt.executeQuery();
    if(results.next()) {
      return results.getInt(EventConstants.EVENT_ID);
    }
    return -1;
  }

  /**
   * Check if event is already in the database
   *
   * @param con       Connection
   * @param eventID   Event ID
   * @return true if event doesn't exist, false otherwise
   */
  public static boolean checkEventExist(Connection con, String eventID)
      throws SQLException {
    String checkEventSql = "SELECT * FROM events WHERE event_id= ?";
    PreparedStatement insertUserStmt =
        con.prepareStatement(checkEventSql, Statement.RETURN_GENERATED_KEYS);
    insertUserStmt.setString(1, eventID);
    ResultSet rs = insertUserStmt.executeQuery();
    return rs.next();
  }
}

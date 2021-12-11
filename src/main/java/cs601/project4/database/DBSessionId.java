package cs601.project4.database;

import cs601.project4.constant.UserConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Query statements for users_session_id table
 *
 * @author marisatania
 */
public class DBSessionId {
  /**
   * Performs SQL insert user to users_session_id table
   * if SQL query is successful, return User object with userid
   * otherwise return null
   *
   * @param con       Connection
   * @param userID    User ID
   * @param sessionID Login info
   * @throws SQLException database access error
   */
  public static void insertUserSessionID(Connection con, int userID, String sessionID) throws SQLException {
    String insertContactSql = "REPLACE INTO users_session_id (session_id, user_id) VALUES (?, ?);";
    PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
    insertContactStmt.setString(1, sessionID);
    insertContactStmt.setInt(2, userID);
    insertContactStmt.executeUpdate();
  }

  /**
   * Get user ID from users_session_id
   * by session_id
   *
   * @param con       Connection
   * @param sessionId String cookie
   * @return userID
   * @throws SQLException database access error
   */
  public static int getUserId(Connection con, String sessionId) throws SQLException {
    String selectUserIdSql = "SELECT user_id FROM users_session_id WHERE session_id = ?;";
    PreparedStatement selectUserIdStmt = con.prepareStatement(selectUserIdSql);
    selectUserIdStmt.setString(1, sessionId);
    ResultSet results = selectUserIdStmt.executeQuery();
    int userId = 0;
    if(results.next()) {
      userId = results.getInt(UserConstants.USERID);
    }
    return userId;
  }

  /**
   * Performs SQL Delete from users_session_id table
   *
   * @param con       Connection
   * @param sessionID Login info
   */
  public static void deleteUserSessionID(Connection con, String sessionID) throws SQLException {
    String deleteUserSessionIdSql = "DELETE FROM users_session_id WHERE session_id = ?;";
    PreparedStatement insertContactStmt = con.prepareStatement(deleteUserSessionIdSql);
    insertContactStmt.setString(1, sessionID);
    insertContactStmt.executeUpdate();
  }

}

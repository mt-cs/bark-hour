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
   * @param username  User object
   * @param sessionID Login info
   * @throws SQLException database access error
   */
  public static void insertUserSessionID(Connection con, String username, String sessionID) throws SQLException {
    String insertContactSql = "REPLACE INTO users_session_id (session_id, username) VALUES (?, ?);";
    PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
    insertContactStmt.setString(1, sessionID);
    insertContactStmt.setString(2, username);
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
    String selectUserIdSql = "SELECT userid FROM users NATURAL JOIN users_session_id WHERE session_id = ?;";
    PreparedStatement selectUserIdStmt = con.prepareStatement(selectUserIdSql);
    selectUserIdStmt.setString(1, sessionId);
    ResultSet results = selectUserIdStmt.executeQuery();
    int userId = 0;
    if(results.next()) {
      userId = results.getInt(UserConstants.USER_ID);
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

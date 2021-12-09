package cs601.project4.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUser {
  private static final Logger logger = LoggerFactory.getLogger(DBManager.class);

  /**
   * Perform update user from users table.
   *
   * @param con      Connection
   * @param username String user name
   * @param email    String user email
   * @param location String location
   * @throws SQLException database access error
   */
  public static void updateUser(
      Connection con,
      String username,
      String email,
      String location)
      throws SQLException {
    String selectUserSql = "UPDATE users SET email = ?, location = ? WHERE username = ?";
    PreparedStatement selectUserStmt = con.prepareStatement(selectUserSql);
    selectUserStmt.setString(1, email);
    selectUserStmt.setString(2, location);
    selectUserStmt.setString(3, username);
    selectUserStmt.executeUpdate();
  }

  /**
   * Perform select user from users table.
   *
   * @param con       Connection
   * @param sessionId Login info
   * @throws SQLException database access error
   */
  public static ResultSet selectUser(Connection con, String sessionId) throws SQLException {
    String selectUserSql = "SELECT * FROM users NATURAL JOIN users_session_id WHERE session_id = ?;";
    PreparedStatement selectUserStmt = con.prepareStatement(selectUserSql);
    selectUserStmt.setString(1, sessionId);
    ResultSet results = selectUserStmt.executeQuery();
    return results;
  }

  /**
   * Performs SQL insert user to users table
   * if SQL query is successful, return User object with userid
   * otherwise return null
   *
   * @param username User object
   * @param email    User email
   * @return true if successful
   */
  public static void insertUser(Connection con, String username, String email) throws SQLException{
    String insertUserSql = "INSERT INTO users (username, email) VALUES (?, ?)";
    PreparedStatement insertUserStmt = con.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS);
    insertUserStmt.setString(1, username);
    insertUserStmt.setString(2, email);
    insertUserStmt.executeUpdate();
  }

  /**
   * Check if data is already in the database
   *
   * @param email User email
   * @return true if data doesn't exist, false otherwise
   */
  public static Boolean checkUserExist(Connection connection, String email) throws SQLException{
    String checkUserSql = "SELECT * FROM users WHERE email = ?";
    PreparedStatement insertUserStmt = connection.prepareStatement(checkUserSql, Statement.RETURN_GENERATED_KEYS);
    insertUserStmt.setString(1, email);
    ResultSet rs = insertUserStmt.executeQuery();
    return rs.next();
  }
}

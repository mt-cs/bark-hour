package cs601.project4.JDBC;

import cs601.project4.JDBC.util.Config;
import cs601.project4.JDBC.util.Utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class DatabaseManager {
  @Value("${spring.datasource.url}")
  private static String url;

  @Value("${spring.datasource.password}")
  private static String password;

  @Value("${spring.datasource.username}")
  private static String username;

  private static final int MIN_IDLE = 5;
  private static final int MAX_IDLE = 10;

  // Apache commons connection pool implementation
  private static final BasicDataSource ds = new BasicDataSource();

  private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

  static {
  /* This code inside the static block is executed only once: the first time the class is loaded into memory.
     -- https://www.geeksforgeeks.org/static-blocks-in-java/
   */
    // TODO: Get url from application property
//    ds.setUrl(url);
//    ds.setUsername(username);
//    ds.setPassword(password);
    ds.setUrl("jdbc:mysql://localhost:3306/user026");
    ds.setUsername("user026");
    ds.setPassword("user026");
    ds.setMinIdle(MIN_IDLE);
    ds.setMaxIdle(MAX_IDLE);
  }

  private DatabaseManager(){ }

  /**
   * Getter for Connection
   *
   * @return  a Connection from the pool.
   * @throws  SQLException on a database access error.
   */
  public static Connection getConnection() throws SQLException {
    return ds.getConnection();
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
  public static boolean insertUser(Connection con, String username, String email) {
    String query = "SELECT * FROM users WHERE email='" + email + "';"; // TODO: Change to prepared
    if (!Utilities.checkDB(query)) {
      return false;
    }
    String insertUserSql = "INSERT IGNORE INTO users (username, email) VALUES (?, ?)";
    try {
      PreparedStatement insertUserStmt = con.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS);
      insertUserStmt.setString(1, username);
      insertUserStmt.setString(2, email);
      insertUserStmt.executeUpdate();
    } catch (SQLException e) {
      logger.error(e.getMessage());
    }
    return true;
  }

  /**
   * Performs SQL insert user to users table
   * if SQL query is successful, return User object with userid
   * otherwise return null
   *
   * @param username  User object
   * @param sessionID Login info
   * @return true if successful
   */
  public static void insertUserSessionID(Connection con, String username, String sessionID) throws SQLException {
    String insertContactSql = "REPLACE INTO users_session_id (session_id, username) VALUES (?, ?);";
    PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
    insertContactStmt.setString(1, sessionID);
    insertContactStmt.setString(2, username);
    insertContactStmt.executeUpdate();
  }


  public static void deleteUserSessionID(Connection con, String sessionID) throws SQLException {
    String deleteUserSessionIdSql = "DELETE FROM users_session_id WHERE session_id = ?;";
    PreparedStatement insertContactStmt = con.prepareStatement(deleteUserSessionIdSql);
    insertContactStmt.setString(1, sessionID);
    insertContactStmt.executeUpdate();
  }


  /**
   * A method to demonstrate using a PrepareStatement to execute a database select
   * @param con
   * @throws SQLException
   */
  public static ResultSet selectUser(Connection con, String sessionId) throws SQLException {
    String selectUserSql = "SELECT * FROM users NATURAL JOIN users_session_id WHERE session_id = ?;";
    PreparedStatement selectUserStmt = con.prepareStatement(selectUserSql);
    selectUserStmt.setString(1, sessionId);
    ResultSet results = selectUserStmt.executeQuery();
//    while(results.next()) {
//      System.out.printf("Name: %s\n", results.getString("name"));
//      System.out.printf("Extension: %s\n", results.getInt("extension"));
//      System.out.printf("Email: %s\n", results.getString("email"));
//      System.out.printf("Start Date: %s\n", results.getString("startdate"));
//    }
    return results;
  }

//
//  /**
//   * delete user by user id in users table
//   *
//   * @param userId
//   * @return
//   */
//  public boolean deleteUser(int userId) {
//    try {
//      PreparedStatement stmt = con.prepareStatement("DELETE FROM users WHERE user_id = ?");
//      stmt.setInt(1, userId);
//      int count = stmt.executeUpdate();
//      if(count == 0) {
//        TicketManagementApplicationLogger.write(Level.WARNING, "User not deleted", 1);
//        return false;
//      }
//      return true;
//    } catch (SQLException e) {
//      TicketManagementApplicationLogger.write(Level.WARNING, "SQL error delete user", 1);
//      return false;
//    }
//  }
//
//  /**
//   * select username of a user from users table
//   *
//   * @param userId
//   * @return
//   */
//  public String selectUser(int userId) {
//    try {
//      PreparedStatement stmt = con.prepareStatement("SELECT username FROM users WHERE user_id = ?");
//      stmt.setInt(1, userId);
//      ResultSet result = stmt.executeQuery();
//      if(!result.next()) {
//        TicketManagementApplicationLogger.write(Level.INFO, "Username not found", 0);
//        return null;
//      }
//      TicketManagementApplicationLogger.write(Level.INFO, "User id: " + userId +" exists", 0);
//      String username = result.getString("username");
//      return username;
//    } catch (SQLException e) {
//      TicketManagementApplicationLogger.write(Level.WARNING, "SQL error select user", 1);
//      return null;
//    }
//  }
//
}

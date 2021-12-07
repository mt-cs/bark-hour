package cs601.project4.JDBC;

import cs601.project4.JDBC.util.Utilities;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
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
    /* Source: https://www.geeksforgeeks.org/static-blocks-in-java/ */
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

  public static int getUserId(Connection con, String sessionId) throws SQLException {
    String selectUserIdSql = "SELECT userid FROM users NATURAL JOIN users_session_id WHERE session_id = ?;";
    PreparedStatement selectUserIdStmt = con.prepareStatement(selectUserIdSql);
    selectUserIdStmt.setString(1, sessionId);
    ResultSet results = selectUserIdStmt.executeQuery();
    int userId = 0;
    if(results.next()) {
      userId = results.getInt("userid");
    }
    return userId;
  }

  /**
   * Performs SQL insert user to users table
   * if SQL query is successful, return User object with userid
   * otherwise return null
   *
   * @return true if successful
   */
  public static boolean createEvent(Connection con, int userId, String eventName, String about, String location, String date, String time, int numTickets) throws SQLException{
  //public static boolean createEvent(Connection con, int userId, String eventName, String about, String location, Date date, Time time, int numTickets) throws SQLException{
    String query = "SELECT * FROM events WHERE event_name='" + eventName + "';"; // TODO: Change to prepared
    if (!Utilities.checkDB(query)) {
      return false;
    }
    String insertEventSql = "INSERT IGNORE INTO events (userid, event_name, location, about,  event_date, event_time, num_ticket_avail) VALUES (?, ?, ?, ?, ?, ?, ?)";
//    String insertEventSql = "INSERT IGNORE INTO events (userid, event_name, location, about, event_date, event_time, num_tickets_available) VALUES (?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement insertUserStmt = con.prepareStatement(insertEventSql, Statement.RETURN_GENERATED_KEYS);
    insertUserStmt.setInt(1, userId);
    insertUserStmt.setString(2, eventName);
    insertUserStmt.setString(3, location);
    insertUserStmt.setString(4, about);
//    insertUserStmt.setInt(5, numTickets);
    insertUserStmt.setString(5, date);
    insertUserStmt.setString(6, time);
    insertUserStmt.setInt(7, numTickets);
    insertUserStmt.executeUpdate();
    return true;
  }

  public static ResultSet selectEvent(Connection con) throws SQLException{
    String selectAllContactsSql = "SELECT * FROM events;";
    PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
    ResultSet results = selectAllContactsStmt.executeQuery();
    return results;
  }

  /**
   * Perform select events by name
   *
   * @param con       Connection
   * @throws SQLException database access error
   */
  public static ResultSet selectEvent(Connection con, String eventName) throws SQLException {
    String selectUserSql = "SELECT * FROM events WHERE event_name = ?;";
    PreparedStatement selectUserStmt = con.prepareStatement(selectUserSql);
    selectUserStmt.setString(1, "Puppy Be Merry");
//    selectUserStmt.setString(1, eventName);
    ResultSet results = selectUserStmt.executeQuery();
    return results;
  }
}

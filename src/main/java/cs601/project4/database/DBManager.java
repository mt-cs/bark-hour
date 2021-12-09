package cs601.project4.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Run JDBC connection
 */
public class DBManager {
  @Value("${spring.datasource.url}")
  private static String url;

  @Value("${spring.datasource.password}")
  private static String password;

  @Value("${spring.datasource.username}")
  private static String username;

  private static final int MIN_IDLE = 5;
  private static final int MAX_IDLE = 10;

  private static final BasicDataSource ds = new BasicDataSource();

  private static final Logger logger = LoggerFactory.getLogger(DBManager.class);

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

  private DBManager(){ }

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
   * @return true if successful
   */
  public static boolean createEvent(Connection con, int userId, String eventName, String about, String location, String date, String time, int numTickets) throws SQLException{
  //public static boolean createEvent(Connection con, int userId, String eventName, String about, String location, Date date, Time time, int numTickets) throws SQLException{
    String query = "SELECT * FROM events WHERE event_name='" + eventName + "';"; // TODO: Change to prepared
    if (!DBUtil.checkDB(query)) {
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

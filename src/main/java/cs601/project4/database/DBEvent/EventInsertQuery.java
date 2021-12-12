package cs601.project4.database.DBEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventInsertQuery {
  private static final Logger logger = LoggerFactory.getLogger(EventInsertQuery.class);

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
        + "(userid, event_name, venue, address, city, state, country, zip,"
        + "about, event_start, event_end, num_ticket, num_ticket_avail) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
    insertEventStmt.setInt(13, numTickets);
    insertEventStmt.executeUpdate();

    ResultSet result = insertEventStmt.getGeneratedKeys();
    if(result.next()) {
      return result.getInt(1);
    }
    logger.warn("Insert event failed");
    return -1;
  }

}

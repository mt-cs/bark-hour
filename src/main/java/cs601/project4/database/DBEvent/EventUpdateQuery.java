package cs601.project4.database.DBEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class EventUpdateQuery {

  /**
   * Update Event
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
   */
  public static void updateEvent(
      Connection con, int eventId, int userId, String eventName, String venue,
      String address, String city, String state, String country, int zip,
      String about, String start, String end, int numTickets)
      throws SQLException {

    String updateEventSql = "UPDATE events "
        + "SET userid = ?, event_name = ?, venue = ?, address = ?, "
        + "city = ?, state = ?, country = ?, zip = ?, about = ?, "
        + "event_start = ?, event_end = ?, num_ticket = ? WHERE event_id = ?;";

    PreparedStatement updateEventStmt =
        con.prepareStatement(updateEventSql, Statement.RETURN_GENERATED_KEYS);
    updateEventStmt.setInt(1, userId);
    updateEventStmt.setString(2, eventName);
    updateEventStmt.setString(3, venue);
    updateEventStmt.setString(4, address);
    updateEventStmt.setString(5, city);
    updateEventStmt.setString(6, state);
    updateEventStmt.setString(7, country);
    updateEventStmt.setInt(8, zip);
    updateEventStmt.setString(9, about);
    updateEventStmt.setString(10, start);
    updateEventStmt.setString(11, end);
    updateEventStmt.setInt(12, numTickets);
    updateEventStmt.setInt(13, eventId);
    updateEventStmt.executeUpdate();
  }

}

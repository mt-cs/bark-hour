package cs601.project4.database;

import cs601.project4.model.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Query statements for tickets table
 *
 * @author marisatania
 */
public class DBTicket {

  /**
   * Inserting a ticket into ticket table
   *
   * @param con        Connection
   * @param ticket     Ticket object
   * @param numTickets number of ticket
   * @return execute batch array result
   * @throws SQLException on a database access error
   */
  public int[] insertTickets(Connection con, Ticket ticket, int numTickets) throws SQLException {
    PreparedStatement insertTicketStmt = con.prepareStatement(
        "INSERT INTO tickets (userid, event_id) VALUES (?, ?)");
    for (int i = 1; i <= numTickets; i++) {
      insertTicketStmt.setInt(1, ticket.getUserId());
      insertTicketStmt.setInt(2, ticket.getEventId());
      insertTicketStmt.addBatch();
    }
    return insertTicketStmt.executeBatch();
  }
}

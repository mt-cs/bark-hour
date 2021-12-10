package cs601.project4.database;

import cs601.project4.model.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Query statements for tickets table
 *
 * @author marisatania
 */
public class DBTicket {
  private static final Logger logger = LoggerFactory.getLogger(DBTicket.class);

  /**
   * Inserting a ticket into ticket table
   *
   * @param con        Connection
   * @param ticket     Ticket object
   * @param numTickets number of ticket
   * @return execute batch array result
   * @throws SQLException on a database access error
   */
  public static boolean insertTickets(Connection con, Ticket ticket, int numTickets) throws SQLException {
    PreparedStatement insertTicketStmt = con.prepareStatement(
        "INSERT INTO tickets (userid, event_id) VALUES (?, ?)");
    for (int i = 1; i <= numTickets; i++) {
      insertTicketStmt.setInt(1, ticket.getUserId());
      insertTicketStmt.setInt(2, ticket.getEventId());
      insertTicketStmt.addBatch();
    }
    int[] executeBatchResult = insertTicketStmt.executeBatch();

    if(executeBatchResult.length != numTickets) {
      logger.warn("Insert tickets failed");
      if(executeBatchResult.length > 0) {
        if(deleteTickets(con, ticket, executeBatchResult.length)) {
          logger.info("Ticket insertion is undone");
        } else {
          logger.warn("Incomplete ticket insertion, revert failed");
        }
      }
      return false;
    }
    return true;
  }

  /**
   * Delete event tickets of a user from the tickets table
   *
   * @param ticket
   * @param numTickets
   * @return
   */
  public static boolean deleteTickets(Connection con, Ticket ticket, int numTickets){
    try {
      PreparedStatement stmt = con.prepareStatement(
          "DELETE FROM tickets WHERE event_id = ? AND user_id = ? ORDER BY ticket_id DESC LIMIT ?");
      stmt.setInt(1, ticket.getEventId());
      stmt.setInt(2, ticket.getUserId());
      stmt.setInt(3, numTickets);
      int count = stmt.executeUpdate();
      if(count == 0) {
        logger.warn("Delete ticket failed");
        return false;
      }
      return true;
    } catch (SQLException e) {
      logger.error(e.getMessage());
      return false;
    }
  }

  /**
   * count number of tickets of an event that user has from tickets table
   *
   * @param userId
   * @param eventId
   * @param eventId
   * @return
   */
  public int countTickets(Connection con, int userId, int eventId) {
    try {
      PreparedStatement stmt = con.prepareStatement("SELECT COUNT(ticket_id) tickets FROM tickets WHERE user_id = ? and event_id = ?");
      stmt.setInt(1, userId);
      stmt.setInt(2, eventId);
      ResultSet result = stmt.executeQuery();
      int ticketCount = 0;
      if(!result.next()) {
        logger.warn("Ticket does not exists");
        return -1;
      } else {
        ticketCount = result.getInt("tickets");
      }
      logger.info("Number of Tickets: " + ticketCount);
      return ticketCount;
    } catch (SQLException e) {
        logger.error(e.getMessage());
        return -1;
    }
  }

  /**
   * Transfer tickets to another user
   *
   * @param userId
   * @param targetUserId
   * @param eventId
   * @param numTickets
   * @return
   */
  public boolean updateTickets(Connection con, int userId, int targetUserId, int eventId, int numTickets) {
    try {
      PreparedStatement stmt = con.prepareStatement(
          "UPDATE tickets SET user_id = ? WHERE user_id = ? and event_id = ? LIMIT ?");
      stmt.setInt(1, targetUserId);
      stmt.setInt(2, userId);
      stmt.setInt(3, eventId);
      stmt.setInt(4, numTickets);
      int count = stmt.executeUpdate();
      if(count == 0) {
        logger.warn("Update tickets failed");
        return false;
      }
      return true;
    } catch (SQLException e) {
      logger.error(e.getMessage());
      return false;
    }
  }
}

package cs601.project4.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Query statements for transactions table
 *
 * @author marisatania
 */
public class DBTransaction {
  /**
   * Inserting a ticket into ticket table
   *
   * @param con        Connection
   * @param numTickets number of ticket
   * @return execute batch array result
   * @throws SQLException on a database access error
   */
  public static boolean insertTransaction(
      Connection con,
      int eventId,
      int ownerId,
      int buyerId,
      int numTickets)
      throws SQLException {

    PreparedStatement insertTransStmt = con.prepareStatement(
        "INSERT INTO transactions (event_id, owner_id, buyer_id, transaction_count) VALUES (?, ?, ?, ?)");
    for (int i = 1; i <= numTickets; i++) {
      insertTransStmt.setInt(1, eventId);
      insertTransStmt.setInt(2, ownerId);
      insertTransStmt.setInt(3, buyerId);
      insertTransStmt.setInt(4, numTickets);
      insertTransStmt.addBatch();
    }
    int[] executeBatchResult = insertTransStmt.executeBatch();

    return executeBatchResult.length > 0;
  }

  /**
   * Get all purchase transactions by buyerId
   *
   * @param con    Connection
   * @param userId int user ID
   * @return results
   * @throws SQLException database access error
   */
  public static ResultSet getPurchase(Connection con, int userId)
      throws SQLException {
    String selectUserSql = "SELECT * FROM transactions WHERE buyer_id = ?;";
    PreparedStatement selectUserStmt = con.prepareStatement(selectUserSql);
    selectUserStmt.setInt(1, userId);
    ResultSet results = selectUserStmt.executeQuery();
    return results;
  }

}

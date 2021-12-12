package cs601.project4.database.DBEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EventSearchQuery {
  /**
   * Partial search event by name
   *
   * @param con   Connection
   * @param input query input String
   * @throws SQLException database access error
   */
  public static ResultSet searchEventByName(Connection con, String input) throws SQLException{
    String searchByNameSql = "SELECT * FROM events WHERE event_name LIKE" + "'%" + input +"%';";
    Statement searchByNameStmt = con.createStatement();
    ResultSet results = searchByNameStmt.executeQuery(searchByNameSql);
    return results;
  }

  // SELECT * FROM events WHERE MONTH(event_start) = MONTH(CURRENT_DATE()) AND YEAR(event_start) = YEAR(CURRENT_DATE());

  // SELECT * FROM events WHERE YEAR(event_start) = YEAR(CURRENT_DATE());



}

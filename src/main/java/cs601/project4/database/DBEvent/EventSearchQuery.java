package cs601.project4.database.DBEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

  /**
   * Partial search event by location
   *
   * @param con   Connection
   * @param input query input String
   * @throws SQLException database access error
   */
  public static ResultSet searchEventByLocation(Connection con, String input) throws SQLException{
    String searchByLocationSql = "SELECT * FROM events WHERE city = ?;";
    PreparedStatement searchByLocationStmt = con.prepareStatement(searchByLocationSql);
    searchByLocationStmt.setString(1, input);
    ResultSet results = searchByLocationStmt.executeQuery();
    return results;
  }

  /**
   * Partial search event that happens today
   *
   * @param con   Connection
   * @param input query input String
   * @throws SQLException database access error
   */
  public static ResultSet searchByCurrentDate(Connection con, String input) throws SQLException{
    String searchByNameSql =
        "SELECT * FROM events WHERE MONTH(event_start) = MONTH(CURRENT_DATE()) AND YEAR(event_start) = YEAR(CURRENT_DATE()) AND DAY(event_start) = DAY(CURRENT_DATE());";
    Statement searchByNameStmt = con.createStatement();
    ResultSet results = searchByNameStmt.executeQuery(searchByNameSql);
    return results;
  }

  /**
   * Partial search event that happens this month
   *
   * @param con   Connection
   * @param input query input String
   * @throws SQLException database access error
   */
  public static ResultSet searchByCurrentMonth(Connection con, String input) throws SQLException{
    String searchByNameSql =
        "SELECT * FROM events WHERE MONTH(event_start) = MONTH(CURRENT_DATE()) AND YEAR(event_start) = YEAR(CURRENT_DATE());";
    Statement searchByNameStmt = con.createStatement();
    ResultSet results = searchByNameStmt.executeQuery(searchByNameSql);
    return results;
  }

  /**
   * Partial search event that happens this year
   *
   * @param con   Connection
   * @param input query input String
   * @throws SQLException database access error
   */
  public static ResultSet searchByCurrentYear(Connection con, String input) throws SQLException{
    String searchByNameSql =
        "SELECT * FROM events WHERE YEAR(event_start) = YEAR(CURRENT_DATE());";
    Statement searchByNameStmt = con.createStatement();
    ResultSet results = searchByNameStmt.executeQuery(searchByNameSql);
    return results;
  }

  // "SELECT * FROM events WHERE MONTH(event_start) = MONTH(CURRENT_DATE()) AND YEAR(event_start) = YEAR(CURRENT_DATE());"

  // SELECT * FROM events WHERE MONTH(event_start) = MONTH(CURRENT_DATE()) AND YEAR(event_start) = YEAR(CURRENT_DATE());

  // "SELECT * FROM events WHERE YEAR(event_start) = YEAR(CURRENT_DATE());";



}

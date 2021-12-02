package cs601.project4.JDBC;

import com.google.gson.Gson;
import cs601.project4.JDBC.util.Config;
import cs601.project4.JDBC.util.Utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

/**
 * Class to demonstrate how to connect to a MySQL database using JDBC.
 * Some code taken from examples here: https://www.baeldung.com/java-jdbc
 * Also see https://www.baeldung.com/java-connection-pooling
 */
public class JDBCExample {

  /**
   * A method to demonstrate using a PreparedStatement to execute a database insert.
   * @param con
   * @param name
   * @param extension
   * @param email
   * @param startdate
   * @throws SQLException
   */
  public static void executeInsert(Connection con, String name, int extension, String email, String startdate) throws SQLException {
    String insertContactSql = "INSERT INTO contacts (name, extension, email, startdate) VALUES (?, ?, ?, ?);";
    PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
    insertContactStmt.setString(1, name);
    insertContactStmt.setInt(2, extension);
    insertContactStmt.setString(3, email);
    insertContactStmt.setString(4, startdate);
    insertContactStmt.executeUpdate();
  }

  /**
   * A method to demonstrate using a PrepareStatement to execute a database select
   * @param con
   * @throws SQLException
   */
  public static void executeSelectAndPrint(Connection con) throws SQLException {
    String selectAllContactsSql = "SELECT * FROM contacts;";
    PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
    ResultSet results = selectAllContactsStmt.executeQuery();
    while(results.next()) {
      System.out.printf("Name: %s\n", results.getString("name"));
      System.out.printf("Extension: %s\n", results.getInt("extension"));
      System.out.printf("Email: %s\n", results.getString("email"));
      System.out.printf("Start Date: %s\n", results.getString("startdate"));
    }
  }

  public static void main(String[] args){
    Config config = Utilities.readConfig();
    if(config == null) {
      System.exit(1);
    }

    // Make sure that mysql-connector-java is added as a dependency.
    // Force Maven to Download Sources and Documentation
    // jdbc:mysql://127.0.0.1:3306/user026
    try (Connection con = DriverManager
        .getConnection("jdbc:mysql://127.0.0.1:3306/" + config.getDatabase(), config.getUsername(), config.getPassword())) {

      executeSelectAndPrint(con);
      System.out.println("*****************");

      executeInsert(con,"Marisa", 2024, "mtania", "2020-12-01");

//      executeSelectAndPrint(con);
//      System.out.println("*****************");
//
//      executeInsert(con,"Bertha", 9876, "bzuniga", "2009-09-01");

      executeSelectAndPrint(con);
      System.out.println("*****************");

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }
}
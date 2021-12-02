//package cs601.project4.JDBC;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
///**
// * An example to demonstrate how to use a ConnectionPool
// */
//public class ConnectionPoolExample {
//
//  public static void main(String[] args) {
//
//    try (Connection connection = DBCPDataSource.getConnection()){
//      JDBCExample.executeInsert(connection, "Jose", 9984, "jsmith", "2026-09-01");
//    } catch(SQLException e) {
//      e.printStackTrace();
//    }
//
//  }
//}
package cs601.project4.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A database utility class.
 *
 * @author marisatania
 */
public class DBUtil {
  private static final Logger logger = LoggerFactory.getLogger(DBManager.class);

  /**
   * Check if data is already in the database
   *
   * @param query   String query
   * @return true if data doesn't exist, false otherwise
   */
  public static Boolean checkDB(String query){
    try (Connection connection = DBManager.getConnection()) {
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery(query);
      return !rs.next();
    }
    catch(SQLException e) {
      logger.info(e.getMessage());
    }
    return false;
  }
}


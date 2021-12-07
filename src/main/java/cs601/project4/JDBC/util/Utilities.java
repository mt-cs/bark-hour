package cs601.project4.JDBC.util;

import com.google.gson.Gson;

import cs601.project4.JDBC.DatabaseManager;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * A database utility class.
 *
 * @author marisatania
 */
public class Utilities {

  public static final String configFileName = "config.json";

  private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

  @Value("${spring.datasource.url}")
  private static String url;

  /**
   * Read in the configuration file.
   *
   * @return config
   */
  public static Config readConfig() {

    Config config = null;
    Gson gson = new Gson();
    try {
      config = gson.fromJson(new FileReader(configFileName), Config.class);
    } catch (FileNotFoundException e) {
      System.err.println("Config file config.json not found: " + e.getMessage());
    }
    return config;
  }

  /**
   * Check if data is already in the database
   *
   * @param query   String query
//   * @param update  String update
   * @return true if data doesn't exist, false otherwise
   */
  public static Boolean checkDB(String query){//}, String update){
    try (Connection connection = DatabaseManager.getConnection()) {
      // create a database connection
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery(query);

      if (rs.next()) {
        return false;
      } else {
        //statement.executeUpdate(update);
        return true;
      }
    }
    catch(SQLException e) {
      logger.info(e.getMessage());
    }
    return false;
  }
}


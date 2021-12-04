package cs601.project4.JDBC;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * A class to store the JDBC properties
 * @author marisatania
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.datasource")
public class Config {
  private String url;
  private String username;
  private String password;

  /**
   * Getter for the database url
   *
   * @return url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Getter for the username property
   *
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Getter for the password
   *
   * @return password
   */
  public String getPassword() {
    return password;
  }
}
package cs601.project4.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * A class to store the JDBC properties
 *
 * @author marisatania
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.datasource")
public class Config {
  private String url;
  private String username;
  private String password;
}
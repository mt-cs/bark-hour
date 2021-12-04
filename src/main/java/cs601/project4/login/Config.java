package cs601.project4.login;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * A class for Slack configuration info
 *
 * @author marisatania
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "slack.config")
public class Config {
  /* These variable names violate Java style guidelines
     in order to be consistent with the naming conventions
     in the Slack API
  */
  private String redirect_uri;
  private String client_id;
  private String client_secret;

  /**
   * Getter for redirect_url
   *
   * @return redirect_url
   */
  public String getRedirect_url() {
    return redirect_uri;
  }

  /**
   * Getter for redirect_url
   *
   * @return client_id
   */
  public String getClient_id() {
    return client_id;
  }

  /**
   * Getter for redirect_url
   *
   * @return client_secret
   */
  public String getClient_secret() {
    return client_secret;
  }
}


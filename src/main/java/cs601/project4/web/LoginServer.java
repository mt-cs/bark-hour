package cs601.project4.web;

import com.google.gson.Gson;
import cs601.project4.login.utilities.Config;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginServer {
  private static final String configFilename = "configSlack.json";

  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(LoginServer.class);
//    try {
//      setUpServer();
//    } catch(Exception e) {
//      logger.error(e.getMessage());
//    }
    SpringApplication.run(LoginServer.class, args);
  }

  /**
   * A helper method to set up the server
   * @throws Exception -- generic Exception thrown by server start method
   */
  public static void setUpServer() throws Exception {
    Gson gson = new Gson();
    Config config = null;
    try {
      config = gson.fromJson(new FileReader(configFilename), Config.class);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
//    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//    context.setAttribute(LoginServerConstants.CONFIG_KEY, config);
  }
}


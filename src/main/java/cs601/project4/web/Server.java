package cs601.project4.web;

import com.google.gson.Gson;
import cs601.project4.login.LoginServerConstants;
import cs601.project4.login.utilities.Config;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Server {
  private static final String configFilename = "configSlack.json";

  public static void main(String[] args) {
    Gson gson = new Gson();
    Config config = null;
    try {
      config = gson.fromJson(new FileReader(configFilename), Config.class);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
//    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//    context.setAttribute(LoginServerConstants.CONFIG_KEY, config);

    SpringApplication.run(Server.class, args);
  }
}


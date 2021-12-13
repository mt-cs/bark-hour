package cs601.project4.web;

import cs601.project4.web.controller.BarkourErrorController;
import cs601.project4.web.controller.HomeController;
import cs601.project4.web.controller.LoginController;
import cs601.project4.web.controller.EventController;
import cs601.project4.web.controller.SearchController;
import cs601.project4.web.controller.TicketController;
import cs601.project4.web.controller.TransactionController;
import cs601.project4.web.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;


@SpringBootTest
class WebControllerTest {

  @Autowired
  private HomeController homeController;

  @Autowired
  private LoginController loginController;

  @Autowired
  private BarkourErrorController errorController;

  @Autowired
  private EventController eventController;

  @Autowired
  private SearchController searchController;

  @Autowired
  private TicketController ticketController;

  @Autowired
  private TransactionController transactionController;

  @Autowired
  private UserController userController;

  @Test
  void contextLoads() {
    assertThat(homeController).isNotNull();
  }

  @Test
  void loginLoads() {
    assertThat(loginController).isNotNull();
  }

  @Test
  void errorLoads() {
    assertThat(errorController).isNotNull();
  }

  @Test
  void searchLoads() {
    assertThat(searchController).isNotNull();
  }

  @Test
  void ticketLoads() {
    assertThat(ticketController).isNotNull();
  }

  @Test
  void transactionLoads() {
    assertThat(transactionController).isNotNull();
  }

  @Test
  void userLoads() {
    assertThat(userController).isNotNull();
  }

  @Test
  void eventLoads() {
    assertThat(errorController).isNotNull();
  }



}
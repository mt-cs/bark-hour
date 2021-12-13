package cs601.project4.web;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  /* Landing page */
  @Test
  public void greetingShouldReturnAppName() throws Exception {
    assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
        String.class)).contains("Barkour");
  }

  @Test
  public void greetingShouldReturnDefaultMessage() throws Exception {
    assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
        String.class)).contains("Ready for a puppy date?");
  }

  @Test
  public void greetingShouldReturnDefaultNav() throws Exception {
    assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
        String.class)).contains("Get started");
  }

  /* Login Error */
  @Test
  public void loginErrorReturnHeader() throws Exception {
    assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/error-login",
        String.class)).contains("Oops...");
  }

  @Test
  public void loginErrorReturnBody() throws Exception {
    assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/error-login",
        String.class)).contains("Please login to your account");
  }

  /* Login */
  @Test
  public void loginReturnHeader() throws Exception {
    assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/login",
        String.class)).contains("Login to Your Account");
  }

  @Test
  public void loginReturnBody() throws Exception {
    assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/login",
        String.class)).contains("We know each other?");
  }

}

package cs601.project4.web;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class BarkourAppTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnDefaultAppName() throws Exception {
    this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("Barkour")));
  }

  @Test
  public void shouldReturnDefaultMessage() throws Exception {
    this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("Ready for a puppy date?")));
  }

  @Test
  public void shouldReturnDefaultNav() throws Exception {
    this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("Get started")));
  }

  /* Login */

  @Test
  public void loginReturnDefaultAppName() throws Exception {
    this.mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("Login to Your Account")));
  }

  @Test
  public void loginReturnDefaultMessage() throws Exception {
    this.mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("We know each other?")));
  }

  /* Login Error */

  @Test
  public void loginErrorReturnDefaultAppName() throws Exception {
    this.mockMvc.perform(get("/error-login")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("Oops...")));
  }

  @Test
  public void loginErrorReturnDefaultMessage() throws Exception {
    this.mockMvc.perform(get("/error-login")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("Please login to your account")));
  }

}

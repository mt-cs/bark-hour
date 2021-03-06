package cs601.project4;

import static org.junit.jupiter.api.Assertions.assertTrue;

import cs601.project4.login.HTTPFetcher;
import cs601.project4.web.HTMLValidator;
import org.junit.jupiter.api.Test;

public class HtmlValidatorTest {
  /**
   * Validate Xhtml for Not found Xhtml
   */
  @Test
  public void validateIndexXhtml() {
    String response = HTTPFetcher.doGet("http://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml for homepage event.html
   */
  @Test
  public void validateHomeXhtml() {
    String response = HTTPFetcher.doGet("http://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/events");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml login.html
   */
  @Test
  public void validateLoginXhtml() {
    String response = HTTPFetcher.doGet("http://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/login");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml users-profile.html
   */
  @Test
  public void validateUsersProfileXhtml() {
    String response = HTTPFetcher.doGet("http://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/users-profile");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml users-profile-form.html
   */
  @Test
  public void validateUsersProfileFormXhtml() {
    String response = HTTPFetcher.doGet("http://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/users-profile-form");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml users-profile-confirmation.html
   */
  @Test
  public void validateUsersProfileConfirmXhtml() {
    String response = HTTPFetcher.doGet("https://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/users-profile-confirmation");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml error-404.html
   */
  @Test
  public void validateError404Xhtml() {
    String response = HTTPFetcher.doGet("https://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/error-404");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml error-500.html
   */
  @Test
  public void validateError500Xhtml() {
    String response = HTTPFetcher.doGet("https://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/error-500");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml error-400.html
   */
  @Test
  public void validateError400Xhtml() {
    String response = HTTPFetcher.doGet("https://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/error-400");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml error-login.html
   */
  @Test
  public void validateErrorLoginXhtml() {
    String response = HTTPFetcher.doGet("https://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/error-login");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml users-events.html
   */
  @Test
  public void validateUsersEventsXhtml() {
    String response = HTTPFetcher.doGet("https://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/users-events");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml events-form.html
   */
  @Test
  public void validateNewEventsXhtml() {
    String response = HTTPFetcher.doGet("https://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/events-form");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml events-status.html
   */
  @Test
  public void validateEventStatusXhtml() {
    String response = HTTPFetcher.doGet("https://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/event-update-status");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml logout.html
   */
  @Test
  public void validateResultsXhtml() {
    String response = HTTPFetcher.doGet("https://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/results");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml tickets.html
   */
  @Test
  public void validateTicketsXhtml() {
    String response = HTTPFetcher.doGet("https://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/tickets");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml logout.html
   */
  @Test
  public void validateTicketsTransferXhtml() {
    String response = HTTPFetcher.doGet("https://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/tickets-transferal");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml transactions.html
   */
  @Test
  public void validateTransactionsXhtml() {
    String response = HTTPFetcher.doGet("https://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/transactions");
    assertTrue(HTMLValidator.isValid(response));
  }

  /**
   * Validate Xhtml
   */
  @Test
  public void validateStatusXhtml() {
    String response = HTTPFetcher.doGet("https://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987f.ngrok.io/tickets-transferal-status");
    assertTrue(HTMLValidator.isValid(response));
  }

}

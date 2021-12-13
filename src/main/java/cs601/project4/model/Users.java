package cs601.project4.model;

/**
 * A class to maintain info about each client.
 */
public class Users {

  private String username;
  private String email;
  private String location;
  private String registrationDate;
  public int userid;

  /**
   * Constructor
   *
   * @param name client name
   */
  public Users(String name, String email) {
    this.username = name;
    this.email = email;
    this.location = "";
    this.registrationDate = "";
  }

  /**
   * Getter for user ID
   *
   * @return userId
   */
  public int getUserId() {
    return userid;
  }

  /**
   * Setter for userId
   *
   * @param userId input userId
   */
  public void setUserId(int userId) {
    this.userid = userId;
  }

  /**
   * Getter for username
   *
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Setter for username
   *
   * @param username input username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Getter for email
   *
   * @return email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Setter for email
   *
   * @param email input email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Getter for location
   *
   * @return location
   */
  public String getLocation() {
    return location;
  }

  /**
   * Setter for location
   *
   * @param location input location
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Getter for registrationDate
   *
   * @return registrationDate
   */
  public String getRegistrationDate() {
    return registrationDate;
  }

  /**
   * Setter for registrationDate
   *
   * @param timestamp input registrationDate
   */
  public void setRegistrationDate(String timestamp) {
    this.registrationDate = timestamp;
  }
}
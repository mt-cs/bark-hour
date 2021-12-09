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
   * Getter for username
   *
   * @return name
   */
  public String getName() {
    return username;
  }

  /**
   * Getter for user ID
   *
   * @return name
   */
  public int getUserId() {
    return userid;
  }

  public void setUserId(int userId) {
    this.userid = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(String timestamp) {
    this.registrationDate = timestamp;
  }
}
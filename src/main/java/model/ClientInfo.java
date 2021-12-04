package model;

/**
 * A class to maintain info about each client.
 */
public class ClientInfo {
  private String username;
  public int userid = 0;

  /**
   * Constructor
   *
   * @param name client name
   */
  public ClientInfo(String name) {
    this.username = name;
    this.userid++;
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


}
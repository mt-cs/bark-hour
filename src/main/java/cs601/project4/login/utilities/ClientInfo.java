package cs601.project4.login.utilities;

/**
 * A class to maintain info about each client.
 */
public class ClientInfo {

  private String name;
  private String token;

  // TODO add token

  /**
   * Constructor
   * @param name
   */
  public ClientInfo(String name) {
    this.name = name;
  }

  /**
   * return name
   * @return
   */
  public String getName() {
    return name;
  }
}
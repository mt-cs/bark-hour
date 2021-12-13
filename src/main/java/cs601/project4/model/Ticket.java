package cs601.project4.model;

/**
 * Model for Ticket
 *
 * @author marisatania
 */
public class Ticket {
  private int ticketId;
  private int userId;
  private int eventId;

  /**
   * Constructor for ticket object
   *
   * @param userId  user ID
   * @param eventId event ID
   */
  public Ticket(int userId, int eventId) {
    this.userId = userId;
    this.eventId = eventId;
  }

  /**
   * Getter for ticketId
   *
   * @return ticketId
   */
  public int getTicketId() {
    return ticketId;
  }

  /**
   * Setter ticket id
   *
   * @param ticketId input ticket id
   */
  public void setTicketId(int ticketId) {
    this.ticketId = ticketId;
  }

  /**
   * Getter for event ID
   *
   * @return eventId
   */
  public int getEventId() {
    return eventId;
  }

  /**
   * Setter for event ID
   *
   * @param eventId input event ID
   */
  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  /**
   * Getter for user ID
   *
   * @return userId
   */
  public int getUserId() {
    return userId;
  }

  /**
   * Setter for userId
   *
   * @param userId input user ID
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }
}

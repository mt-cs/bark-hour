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

  public Ticket(int userId, int eventId) {
    this.userId = userId;
    this.eventId = eventId;
  }

  public int getTicketId() {
    return ticketId;
  }

  public void setTicketId(int ticketId) {
    this.ticketId = ticketId;
  }

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }
}

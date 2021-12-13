package cs601.project4.model;

/**
 * Model for a user's ticket
 *
 * @author marisatania
 */
public class UserTicket {
  public int eventId;
  public String eventName;
  public String organizer;
  public int ticketCount;


  public UserTicket(
      int eventId,
      String eventName,
      int ticketCount,
      String organizer) {
    this.eventId = eventId;
    this.eventName = eventName;
    this.ticketCount = ticketCount;
    this.organizer = organizer;
  }

  /**
   * Getter for eventId
   *
   * @return eventId
   */
  public int getEventId() {
    return eventId;
  }

  /**
   * Setter for eventId
   *
   * @param eventId input eventId
   */
  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  /**
   * Getter for eventName
   *
   * @return eventName
   */
  public String getEventName() {
    return eventName;
  }

  /**
   * Setter for eventName
   *
   * @param eventName input eventName
   */
  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  /**
   * Getter for ticketCount
   *
   * @return ticketCount
   */
  public int getTicketCount() {
    return ticketCount;
  }

  /**
   * Setter for ticketCount
   *
   * @param ticketCount input ticketCount
   */
  public void setTicketCount(int ticketCount) {
    this.ticketCount = ticketCount;
  }
}
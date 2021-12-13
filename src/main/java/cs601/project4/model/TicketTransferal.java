package cs601.project4.model;

/**
 * Model for a user's ticket
 *
 * @author marisatania
 */
public class TicketTransferal {
  public int ticketId;
  public int eventId;
  public String eventName;
  public String organizer;
  public int ticketCount;

  /**
   * Getter for ticketId
   *
   * @return event ticketId
   */
  public int getTicketId() {
    return ticketId;
  }

  /**
   * Setter for ticketId
   *
   * @param ticketId input ticketId
   */
  public void setTicketId(int ticketId) {
    this.ticketId = ticketId;
  }

  /**
   * Setter for organizer
   *
   * @param organizer input organizer
   */
  public void setOrganizer(String organizer) {
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
   * Getter for end date
   *
   * @return event end
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
   * Setter for venue
   *
   * @param ticketCount input ticketCount
   */
  public void setTicketCount(int ticketCount) {
    this.ticketCount = ticketCount;
  }
}
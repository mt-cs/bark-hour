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


  public UserTicket(int eventId, String eventName, int ticketCount, String organizer) {
    this.eventId = eventId;
    this.eventName = eventName;
    this.ticketCount = ticketCount;
    this.organizer = organizer;
  }

  public String getOrganizer() {
    return organizer;
  }

  public void setOrganizer(String organizer) {
    this.organizer = organizer;
  }

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public int getTicketCount() {
    return ticketCount;
  }

  public void setTicketCount(int ticketCount) {
    this.ticketCount = ticketCount;
  }
}
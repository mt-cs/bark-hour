package cs601.project4.model;


import java.sql.Timestamp;

/**
 * Event model
 *
 * @author marisatania
 */
public class Event {
  private int eventId;
  private String eventName;
  private String about;
  private String location;
  private Timestamp eventStart;
  private Timestamp eventEnd;
  private int userId;
  private int numTicket;
  private int numTicketAvail;
  private int numTicketPurchased;

  /**
   * Constructor for Event model
   *
   * @param eventId            int event ID
   * @param eventName          String event name
   * @param about              String event description
   * @param location           String location
   * @param eventStart         Timestamp event start date
   * @param eventEnd           Timestamp event end date
   * @param userId             int user ID
   * @param numTicket          int capacity
   * @param numTicketAvail     int available ticket count
   * @param numTicketPurchased int ticket purchased count
   */
  public Event(int eventId,
               String eventName,
               String about,
               String location,
               Timestamp eventStart,
               Timestamp eventEnd,
               int userId,
               int numTicket,
               int numTicketAvail,
               int numTicketPurchased) {
    this.eventId = eventId;
    this.eventName = eventName;
    this.about = about;
    this.location = location;
    this.eventStart = eventStart;
    this.eventEnd = eventEnd;
    this.userId = userId;
    this.numTicket = numTicket;
    this.numTicketAvail = numTicketAvail;
    this.numTicketPurchased = numTicketPurchased;
  }

  /**
   * Default constructor
   */
  public Event() {}

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
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

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getNumTickets() {
    return numTicket;
  }

  public void setNumTickets(int numTickets) {
    this.numTicket = numTickets;
  }

  public int getNumTicketAvail() {
    return numTicketAvail;
  }

  public void setNumTicketAvail(int numTicketAvail) {
    this.numTicketAvail = numTicketAvail;
  }

  public int getNumTicketPurchased() {
    return numTicketPurchased;
  }

  public void setNumTicketPurchased(int numTicketPurchased) {
    this.numTicketPurchased = numTicketPurchased;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Timestamp getEventStart() {
    return eventStart;
  }

  public void setEventStart(Timestamp eventStart) {
    this.eventStart = eventStart;
  }

  public Timestamp getEventEnd() {
    return eventEnd;
  }

  public void setEventEnd(Timestamp eventEnd) {
    this.eventEnd = eventEnd;
  }

  @Override
  public String toString() {
    return "Event{" +
        "eventId=" + eventId +
        ", eventName='" + eventName + '\'' +
        ", about='" + about + '\'' +
        ", location='" + location + '\'' +
        ", eventStart=" + eventStart +
        ", eventEnd=" + eventEnd +
        ", userId=" + userId +
        ", numTicket=" + numTicket +
        ", numTicketAvail=" + numTicketAvail +
        ", numTicketPurchased=" + numTicketPurchased +
        '}';
  }
}

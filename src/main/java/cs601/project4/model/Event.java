package cs601.project4.model;

import java.sql.Date;
import java.sql.Time;

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
  private Date date;
  private Time time;
  private int userId;
  private int numTickets;
  private int numTicketAvail;
  private int numTicketPurchased;

  public Event(int eventId, String eventName, int numTickets, int numTicketAvail, int numTicketPurchased) {
    this.eventId = eventId;
    this.eventName = eventName;
    this.numTickets = numTickets;
    this.numTicketAvail = numTicketAvail;
    this.numTicketPurchased = numTicketPurchased;
  }

  public Event(int eventId, String eventName, String about, String location, Date date, Time time,
      int userId,
      int numTickets, int numTicketAvail, int numTicketPurchased) {
    this.eventId = eventId;
    this.eventName = eventName;
    this.about = about;
    this.location = location;
    this.date = date;
    this.time = time;
    this.userId = userId;
    this.numTickets = numTickets;
    this.numTicketAvail = numTicketAvail;
    this.numTicketPurchased = numTicketPurchased;
  }

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
    return numTickets;
  }

  public void setNumTickets(int numTickets) {
    this.numTickets = numTickets;
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

  public Time getTime() {
    return time;
  }

  public void setTime(Time time) {
    this.time = time;
  }
}
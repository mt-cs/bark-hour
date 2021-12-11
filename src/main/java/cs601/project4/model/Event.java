package cs601.project4.model;


import java.sql.Timestamp;

/**
 * Event model
 *
 * @author marisatania
 */
public class Event {
  // TODO : add image
  // TODO : add foreign key
  private int eventId;
  private String eventName;
  private String about;
  private String venue;
  private String address;
  private String city;
  private String state;
  private String country;
  private int zip;
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
   * @param venue              String venue
   * @param address            String address
   * @param city               String city
   * @param state              String state
   * @param country            String country
   * @param zip                int zip
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
               String venue,
               String address,
               String city,
               String state,
               String country,
               int zip,
               Timestamp eventStart,
               Timestamp eventEnd,
               int userId,
               int numTicket,
               int numTicketAvail,
               int numTicketPurchased) {
    this.eventId = eventId;
    this.eventName = eventName;
    this.about = about;
    this.address = address;
    this.city = city;
    this.state = state;
    this.country = country;
    this.zip = zip;
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
    return address + ", " + city + ", " + state + ", " + country;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public int getZip() {
    return zip;
  }

  public void setZip(int zip) {
    this.zip = zip;
  }

  public int getNumTicket() {
    return numTicket;
  }

  public void setNumTicket(int numTicket) {
    this.numTicket = numTicket;
  }

  public String getVenue() {
    return venue;
  }

  public void setVenue(String venue) {
    this.venue = venue;
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
}

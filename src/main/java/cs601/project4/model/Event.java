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
    this.venue = venue;
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

  /**
   * Getter for about description
   *
   * @return about
   */
  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

  /**
   * Getter for event ID
   *
   * @return eventId
   */
  public int getEventId() {
    return eventId;
  }


  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  /**
   * Getter for event name
   *
   * @return eventName
   */
  public String getEventName() {
    return eventName;
  }

  /**
   *
   * @param eventName
   */
  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  /**
   * Getter for user ID
   *
   * @return userId
   */
  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  /**
   * Getter for number of tickets
   *
   * @return numTicket
   */
  public int getNumTickets() {
    return numTicket;
  }

  /**
   * Set number of tickets
   *
   * @param numTickets number of tickets
   */
  public void setNumTickets(int numTickets) {
    this.numTicket = numTickets;
  }

  /**
   * Set ticket availability
   *
   * @param numTicketAvail number of slot left
   */
  public void setNumTicketAvail(int numTicketAvail) {
    this.numTicketAvail = numTicketAvail;
  }

  /**
   * Getter for numTicketAvail
   *
   * @return numTicketAvail
   */
  public int getNumTicketAvail() {
    return numTicketAvail;
  }

  /**
   * Getter for num ticket purchased
   *
   * @return numTicketPurchased
   */
  public int getNumTicketPurchased() {
    return numTicketPurchased;
  }

  /**
   * Set number of ticket sold
   *
   * @param numTicketPurchased int number of purchased ticker
   */
  public void setNumTicketPurchased(int numTicketPurchased) {
    this.numTicketPurchased = numTicketPurchased;
  }

  /**
   * Getter for location
   *
   * @return location
   */
  public String getLocation() {
    return address + ", " + city + ", " + state + ", " + country;
  }

  /**
   * Getter for address
   *
   * @return address
   */
  public String getAddress() {
    return address;
  }

  /**
   * Set event address
   *
   * @param address String address input
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Getter for city
   *
   * @return city
   */
  public String getCity() {
    return city;
  }

  /**
   * Setter for city
   *
   * @param city input city
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * Getter for state
   *
   * @return state
   */
  public String getState() {
    return state;
  }

  /**
   * Setter for state
   *
   * @param state input state
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * Getter for country
   *
   * @return country
   */
  public String getCountry() {
    return country;
  }

  /**
   * Setter for country
   *
   * @param country input country
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * Getter for zip
   *
   * @return zip
   */
  public int getZip() {
    return zip;
  }

  /**
   * Setter for zip
   *
   * @param zip input zip
   */
  public void setZip(int zip) {
    this.zip = zip;
  }

  /**
   * Getter for ticket number
   *
   * @return numTicket
   */
  public int getNumTicket() {
    return numTicket;
  }

  /**
   * Setter for number of tickets
   *
   * @param numTicket input capacity
   */
  public void setNumTicket(int numTicket) {
    this.numTicket = numTicket;
  }

  /**
   * Getter for venue
   *
   * @return venue
   */
  public String getVenue() {
    return venue;
  }

  /**
   * Setter for venue
   *
   * @param venue input venue
   */
  public void setVenue(String venue) {
    this.venue = venue;
  }

  /**
   * Getter for start date
   *
   * @return event start
   */
  public Timestamp getEventStart() {
    return eventStart;
  }

  /**
   * Set end date
   *
   * @param eventStart Timestamp start date
   */
  public void setEventStart(Timestamp eventStart) {
    this.eventStart = eventStart;
  }

  /**
   * Getter for end date
   *
   * @return event end
   */
  public Timestamp getEventEnd() {
    return eventEnd;
  }

  /**
   * Set end date
   *
   * @param eventEnd Timestamp end date
   */
  public void setEventEnd(Timestamp eventEnd) {
    this.eventEnd = eventEnd;
  }
}
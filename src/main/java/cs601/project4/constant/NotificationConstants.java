package cs601.project4.constant;


/**
 * A helper class to maintain notification msgs
 *
 * @author marisatania
 */
public class NotificationConstants {

  public static final String MSG = "msg";

  /* Ticket Notifications */
  public static final String NOTIFY_MIN_TICKET = "Number of tickets has to be at least 1. Please try again.";
  public static final String NOTIFY_SOLD_OUT = "Sorry, this event is sold out. Please try again.";
  public static final String NOTIFY_SPACE = "Sorry, not enough space available. Please try again.";
  public static final String NOTIFY_OWNER = "You are already the owner of this event. Please try again.";
  public static final String NOTIFY_FAIL = "Failed to purchase ticket. Please try again.";
  public static final String NOTIFY_SUCCESS = "Registration successful, woof!";

  public static final String NOTIFY_EMAIL = "Recipient email is not registered. Please try again.";
  public static final String NOTIFY_OTHER = "You can only transfer to other users. Please try again.";
  public static final String NOTIFY_ORGANIZER = "Can't transfer to the organizer of this event. Please try again.";
  public static final String NOTIFY_TRANSFER_FAIL = "Ticket transfer failed. Please try again.";
  public static final String NOTIFY_TRANSFER_SUCCESS = "Transfer successful, woof!";

  /* Event Notification */
  public static final String NOTIFY_EVENT_NOTFOUND = "Event not found. Please try again.";
  public static final String NOTIFY_NOT_ORGANIZER = "You are not the organizer of this event. Please try again.";
  public static final String NOTIFY_EVENT_UPDATE_SUCCESS = "Event update successful, woof!";

  public static final String NOTIFY_END_DATE = "Start date can not be later than end date. Please try again.";
  public static final String NOTIFY_DATE_PAST = "Date cannot be in the past. Please try again.";
  public static final String NOTIFY_EVENT_EXIST = "Event already exists. Please try again.";
  public static final String NOTIFY_EVENT_FAIL = "Failed to create event. Please try again.";
  public static final String NOTIFY_EVENT_SUCCESS = "Woof, success! Your event is now up for grab.";

  /* User Notification */
  public static final String NOTIFY_USER_UPDATE_FAIL = "Failed to update profile. Please try again.";
  public static final String NOTIFY_USER_UPDATE_SUCCESS = "Your profile has been updated, woof!";

}

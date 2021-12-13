package cs601.project4.model;

/**
 * Transaction object
 *
 * @author marisatania
 */
public class Transaction {
  public int transactionId;
  public int eventId;
  public int ownerId;
  public int buyerId;
  public int transactionCount;
  public String timestamp;
  public String eventName;
  public String ownerName;
  public String buyerName;

  /**
   * Getter for buyerName
   *
   * @return buyerName
   */
  public String getBuyerName() {
    return buyerName;
  }

  /**
   * Setter for buyerName
   *
   * @param buyerName input buyerName
   */
  public void setBuyerName(String buyerName) {
    this.buyerName = buyerName;
  }

  /**
   * Getter for ownerName
   *
   * @return ownerName
   */
  public String getOwnerName() {
    return ownerName;
  }

  /**
   * Setter for ownerName
   *
   * @param ownerName input ownerName
   */
  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
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
   * Getter for transactionId
   *
   * @return transactionId
   */
  public int getTransactionId() {
    return transactionId;
  }

  /**
   * Setter for transactionId
   *
   * @param transactionId input transactionId
   */
  public void setTransactionId(int transactionId) {
    this.transactionId = transactionId;
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
   * Getter for ownerId
   *
   * @return ownerId
   */
  public int getOwnerId() {
    return ownerId;
  }

  /**
   * Setter for ownerId
   *
   * @param ownerId input ownerId
   */
  public void setOwnerId(int ownerId) {
    this.ownerId = ownerId;
  }

  /**
   * Getter for buyerId
   *
   * @return buyerId
   */
  public int getBuyerId() {
    return buyerId;
  }

  /**
   * Setter for buyerId
   *
   * @param buyerId input buyerId
   */
  public void setBuyerId(int buyerId) {
    this.buyerId = buyerId;
  }

  /**
   * Getter for transactionCount
   *
   * @return transactionCount
   */
  public int getTransactionCount() {
    return transactionCount;
  }

  /**
   * Setter for transactionCount
   *
   * @param transactionCount input transactionCount
   */
  public void setTransactionCount(int transactionCount) {
    this.transactionCount = transactionCount;
  }

  /**
   * Getter for timestamp
   *
   * @return timestamp
   */
  public String getTimestamp() {
    return timestamp;
  }

  /**
   * Setter for timestamp
   *
   * @param timestamp input timestamp
   */
  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }
}

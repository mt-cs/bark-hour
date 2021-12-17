package cs601.project4.web.controller;

import static cs601.project4.web.WebUtilities.validateLogin;

import cs601.project4.constant.TransactionConstants;
import cs601.project4.database.DBEvent.EventSelectQuery;
import cs601.project4.database.DBManager;
import cs601.project4.database.DBSessionId;
import cs601.project4.database.DBTransaction;
import cs601.project4.database.DBUser;
import cs601.project4.model.Transaction;
import cs601.project4.web.WebUtilities;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for transactions
 *
 * @author marisatania
 */
@Controller
public class TransactionController {
  private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

  /**
   * Display all user's transactions
   *
   * @param model Model
   * @return ticket
   */
  @GetMapping(value={"/transactions"})
  public String getTransactions(Model model, HttpServletRequest request) {

    String sessionId = validateLogin(request);

    List<Transaction> transactionList = new ArrayList<>();
    List<String> headers = Arrays.asList(
        TransactionConstants.HEADERS_ID,
        TransactionConstants.HEADERS_EVENT_ID,
        TransactionConstants.HEADERS_EVENT,
        TransactionConstants.HEADERS_OWNER_ID,
        TransactionConstants.HEADERS_FROM,
        TransactionConstants.HEADERS_COUNT,
        TransactionConstants.HEADERS_DATE
    );

    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      ResultSet results = DBTransaction.getPurchase(con, userId);
      while(results.next()) {

        int eventId = results.getInt(TransactionConstants.EVENT_ID);

        String eventName = EventSelectQuery.getEventName(con, eventId);
        if (eventName == null) {
          logger.warn("Event doesn't exist");
        }
        String ownerName = DBUser.getUserName(con,
            results.getInt(TransactionConstants.OWNER_ID));

        Transaction transaction = new Transaction();
        transaction.setEventId(eventId);
        transaction.setBuyerId(userId);
        transaction.setOwnerName(ownerName);
        transaction.setEventName(eventName);
        transactionList.add(transaction);

        transaction.setTransactionId(results.getInt
            (TransactionConstants.TRANSACTION_ID));
        transaction.setTransactionCount(results.getInt
            (TransactionConstants.COUNT));
        transaction.setOwnerId(results.getInt
            (TransactionConstants.OWNER_ID));
        transaction.setTimestamp
            (WebUtilities.getTimestampString
            (results.getTimestamp
            (TransactionConstants.TIMESTAMP)));

      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute("headers", headers);
    model.addAttribute("transactions", transactionList);
    return "transactions";
  }

  /**
   * Display all user's transactions
   *
   * @param model Model
   * @return ticket
   */
  @GetMapping(value={"/transactions-transferal"})
  public String getTransfers(Model model, HttpServletRequest request) {

    String sessionId = validateLogin(request);

    List<Transaction> transactionList = new ArrayList<>();
    List<String> headers = Arrays.asList(
        TransactionConstants.HEADERS_ID,
        TransactionConstants.HEADERS_EVENT_ID,
        TransactionConstants.HEADERS_EVENT,
        TransactionConstants.HEADERS_BUYER_ID,
        TransactionConstants.HEADERS_TO,
        TransactionConstants.HEADERS_COUNT,
        TransactionConstants.HEADERS_DATE
    );

    try (Connection con = DBManager.getConnection()) {
      int userId = DBSessionId.getUserId(con, sessionId);
      ResultSet results = DBTransaction.getTransfers(con, userId);

      while(results.next()) {
        int eventId = results.getInt(TransactionConstants.EVENT_ID);
        int organizerId = EventSelectQuery.getUserIdByEventId(con, eventId);
        if (organizerId == userId) {
          continue;
        }

        String eventName = EventSelectQuery.getEventName(con, eventId);
        if (eventName == null) {
          logger.warn("Event doesn't exist");
        }

        Transaction transaction = new Transaction();

        transaction.setEventId(eventId);
        transaction.setEventName(eventName);

        transaction.setTransactionId
            (results.getInt(TransactionConstants.TRANSACTION_ID));
        transaction.setTransactionCount
            (results.getInt(TransactionConstants.COUNT));
        transaction.setBuyerId
            (results.getInt(TransactionConstants.BUYER_ID));

        transaction.setOwnerId
            (results.getInt(TransactionConstants.OWNER_ID));
        transaction.setBuyerName
            (DBUser.getUserName
            (con, results.getInt(TransactionConstants.BUYER_ID)));
        transaction.setOwnerName
            (DBUser.getUserName
            (con, results.getInt(TransactionConstants.OWNER_ID)));
        transaction.setTimestamp
            (WebUtilities.getTimestampString
            (results.getTimestamp(TransactionConstants.TIMESTAMP)));

        transactionList.add(transaction);
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute("headers", headers);
    model.addAttribute("transactions", transactionList);
    return "transactions-transferal";
  }
}




package cs601.project4.web.controller;

import cs601.project4.constant.EventConstants;
import cs601.project4.constant.TransactionConstants;
import cs601.project4.database.DBEvent;
import cs601.project4.database.DBManager;
import cs601.project4.database.DBSessionId;
import cs601.project4.database.DBTransaction;
import cs601.project4.database.DBUser;
import cs601.project4.model.Transaction;
import cs601.project4.web.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }
    String sessionId = session.getId();

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
        Transaction transaction = new Transaction();
        transaction.setTransactionId(results.getInt(TransactionConstants.TRANSACTION_ID));
        int eventId = results.getInt(TransactionConstants.EVENT_ID);
        transaction.setEventId(eventId);
        String eventName = DBEvent.getEventName(con, eventId);
        if (eventName == null) {
          logger.warn("Event doesn't exist");
        }
        transaction.setEventName(eventName);
        transaction.setTransactionCount(results.getInt(TransactionConstants.COUNT));
        transaction.setBuyerId(userId);
        String ownerName = DBUser.getUserName(con, results.getInt(TransactionConstants.OWNER_ID));
        transaction.setOwnerName(ownerName);
        transaction.setOwnerId(results.getInt(TransactionConstants.OWNER_ID));
        transaction.setTimestamp(Util.getTimestampString(results.getTimestamp(TransactionConstants.TIMESTAMP)));
        transactionList.add(transaction);
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
  @GetMapping(value={"/transfers"})
  public String getTransfers(Model model, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {
      return "redirect:/error-login";
    }
    String sessionId = session.getId();

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
        int organizerId = DBEvent.getUserIdByEventId(con, eventId);
        if (organizerId == userId) {
          continue;
        }
        Transaction transaction = new Transaction();
        transaction.setTransactionId(results.getInt(TransactionConstants.TRANSACTION_ID));

        transaction.setEventId(eventId);
        String eventName = DBEvent.getEventName(con, eventId);
        if (eventName == null) {
          logger.warn("Event doesn't exist");
        }
        transaction.setEventName(eventName);
        transaction.setTransactionCount(results.getInt(TransactionConstants.COUNT));
        transaction.setBuyerId(results.getInt(TransactionConstants.BUYER_ID));
        transaction.setBuyerName(DBUser.getUserName(con, results.getInt(TransactionConstants.BUYER_ID)));
        transaction.setOwnerId(results.getInt(TransactionConstants.OWNER_ID));
        transaction.setOwnerName(DBUser.getUserName(con, results.getInt(TransactionConstants.OWNER_ID)));
        transaction.setTimestamp(Util.getTimestampString(results.getTimestamp(TransactionConstants.TIMESTAMP)));
        transactionList.add(transaction);
      }
    } catch (SQLException sqlException) {
      logger.error(sqlException.getMessage());
    }
    model.addAttribute("headers", headers);
    model.addAttribute("transactions", transactionList);
    return "transfers";
  }

}




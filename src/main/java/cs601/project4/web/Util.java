package cs601.project4.web;

import cs601.project4.constant.NotificationConstants;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

/**
 * A helper class for various web classes
 */
public class Util {
  private static final Logger logger = LoggerFactory.getLogger(Util.class);

  /**
   * A helper class to get Timestamp from String
   *
   * @param timestamp timestamp
   * @return newTimestamp
   */
  public static String getTimestampString(Timestamp timestamp) {
    String timestampStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(timestamp);
    StringBuilder newTimestampSb = new StringBuilder(timestampStr);
    return newTimestampSb.toString();
  }

  /**
   * A helper class to validate Login
   *
   * @param request HttpServletRequest request
   * @return sessionId
   */
  public static String validateLogin(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {
      return null;
    }
    return session.getId();
  }

  /**
   * A helper class to notify failed transaction
   *
   * @param model Model
   * @param msg   String notification message
   */
  public static void notifyFailedQuery(Model model, String msg) {
    logger.warn(msg);
    model.addAttribute(NotificationConstants.MSG, msg);;
  }

}

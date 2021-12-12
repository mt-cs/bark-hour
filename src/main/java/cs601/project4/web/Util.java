package cs601.project4.web;

import cs601.project4.constant.NotificationConstants;
import cs601.project4.web.controller.TransactionController;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

public class Util {
  private static final Logger logger = LoggerFactory.getLogger(Util.class);
  public static String getTimestampString(Timestamp timestamp) {
    String timestampStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(timestamp);
    StringBuilder newTimestampSb = new StringBuilder(timestampStr);
    return newTimestampSb.toString();
  }

  public static Timestamp formatTimestamp (String dateTime) {
    StringBuilder newTimestampSb = new StringBuilder(dateTime);
    newTimestampSb.setCharAt(10, 'T');
    newTimestampSb.append(":00");

    Date parsedDate = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    try {
      parsedDate = dateFormat.parse(newTimestampSb.toString());
    } catch (ParseException e) {
      logger.error(e.getMessage());
    }
    Timestamp timestamp = new Timestamp(parsedDate.getTime());
    return timestamp;
  }

  public static void notifyFailedQuery(Model model, String msg) {
    logger.warn(msg);
    model.addAttribute(NotificationConstants.MSG, msg);;
  }


}

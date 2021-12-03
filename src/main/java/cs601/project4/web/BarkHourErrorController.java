package cs601.project4.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BarkHourErrorController implements ErrorController {
  private final Logger logger = LoggerFactory.getLogger(BarkHourErrorController.class);
  @RequestMapping("/error")
  public String handleError(HttpServletRequest request) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    logger.error("ERROR: " + status.toString());
    int statusCode = Integer.parseInt(status.toString());
    if (statusCode == HttpStatus.NOT_FOUND.value()) {
      return "error-404";
    } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
      return "error-500";
    }
    return "error";
  }
}
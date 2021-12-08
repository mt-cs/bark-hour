package cs601.project4.web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for error page
 *
 * @author marisatania
 */
@Controller
public class BarkHourErrorController implements ErrorController {
  private final Logger logger = LoggerFactory.getLogger(BarkHourErrorController.class);

  /**
   * Handle error path
   *
   * @param request HttpServletRequest
   * @return error page
   */
  @RequestMapping("/error")
  public String handleError(HttpServletRequest request) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    logger.error("ERROR: " + status.toString());
    int statusCode = Integer.parseInt(status.toString());
    if (statusCode == HttpStatus.NOT_FOUND.value()) {
      return "pages-error-404";
    } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
      return "error500";
    }
    return "error";
  }
}
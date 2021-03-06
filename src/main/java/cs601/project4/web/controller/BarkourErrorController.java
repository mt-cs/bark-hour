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
public class BarkourErrorController implements ErrorController {
  private final Logger logger = LoggerFactory.getLogger(BarkourErrorController.class);

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
      return "redirect:/error-404";
    } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
      return "redirect:/error-500";
    } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
      return "redirect:/error-400";
    }
    return "error";
  }

  /**
   * Handles 404 error
   *
   * @return error-404
   */
  @RequestMapping("/error-404")
  public String handle404Error() {
    return "error-404";
  }

  /**
   * Handles 400 error
   *
   * @return error-400
   */
  @RequestMapping("/error-400")
  public String handle400Error() {
    return "error-400";
  }

  /**
   * Handles 500 error
   *
   * @return error-500
   */
  @RequestMapping("/error-500")
  public String handle500Error() {
    return "error-500";
  }

  /**
   * Handles login error
   *
   * @return login-error
   */
  @RequestMapping("/error-login")
  public String handleLoginError() {
    return "error-login";
  }
}
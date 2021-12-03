package cs601.project4.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BarkHourErrorController implements ErrorController {
  @RequestMapping("/error")
  public String handleError() {
    //TODO: Log error
    return "error";
  }
}
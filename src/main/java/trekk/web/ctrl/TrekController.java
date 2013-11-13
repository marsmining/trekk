package trekk.web.ctrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TrekController {

    private static final Logger log = LoggerFactory.getLogger(TrekController.class);

    @RequestMapping("/")
    public String home() {
        log.debug("home");
        return "home";
    }

    @RequestMapping("/customer")
    public String list() {
        log.debug("list");
        return "home";
    }

    @RequestMapping("/customer/{id}")
    public String detail() {
        log.debug("detail");
        return "home";
    }

    @RequestMapping("/customer/{id}/navigation")
    public String navigation() {
        log.debug("navigation");
        return "home";
    }
}

package trekk.web;

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
}

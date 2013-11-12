package trekk.web.ctrl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import trekk.server.io.TrekkService;
import trekk.server.model.Customer;

@Controller
@RequestMapping("/api/customer")
public class CustomerResource {

    private static final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    private @Inject TrekkService trekkService;

    @RequestMapping(value="", method=RequestMethod.GET)
    public void masterGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        log.debug("masterGet");
        Iterable<Customer> xs = trekkService.findAll();
        for (Customer c: xs) {
            log.debug("found customer: {}", c);
        }
        writeJson("[]", response);
    }

    public static void writeJson(final String json, final HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(json);
        out.close();
    }
}

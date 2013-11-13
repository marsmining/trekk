package trekk.web.ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import trekk.common.JsonUtil;
import trekk.common.Views;
import trekk.server.io.TrekkService;
import trekk.server.model.Customer;

import com.google.common.collect.ImmutableList;

@Controller
@RequestMapping("/api/customer")
public class CustomerResource {

    private static final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    private static final String APP_JSON = "application/json";

    private @Inject TrekkService trekkService;
    private @Inject Validator validator;

    @RequestMapping(value="", method=RequestMethod.GET)
    public void customerGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        log.debug("customerGet");
        Iterable<Customer> xs = trekkService.findAll();
        writeJson(JsonUtil.serialize(xs, Views.Public.class), response, null);
    }

    /**
     * List. HTTP GET.
     */
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public void customerGet(@PathVariable Long id,
            HttpServletResponse response) throws IOException {
        log.debug("customerGet({})", id);
        writeJson(JsonUtil.serialize(trekkService.findCustomer(id), Views.Public.class),
                response, null);
    }

    /**
     * Create. HTTP POST.
     */
    @RequestMapping(method=RequestMethod.POST, consumes=APP_JSON)
    public void customerPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        log.debug("customerPost");

        Customer customer = JsonUtil.parse(request.getInputStream(), Customer.class);
        if (customer == null) {
            writeJson(JsonUtil.serializeBean(ImmutableList.of("parse failed")),
                    response, 400);
            return;
        }
        Set<ConstraintViolation<Customer>> errors = validator.validate(customer);
        if (errors.isEmpty()) {
            writeJson(JsonUtil.serializeBean(trekkService.create(customer)), response, null);
        } else {
            writeJson(JsonUtil.serializeBean(convert(errors)), response, 400);
        }
    }

    /**
     * Update. HTTP PUT.
     */
    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes=APP_JSON)
    public void customerPut(@PathVariable Long id, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        log.debug("customerPut({})", id);

        Customer customer = JsonUtil.parse(request.getInputStream(), Customer.class);
        if (customer == null) {
            writeJson(JsonUtil.serializeBean(ImmutableList.of("parse failed")),
                    response, 400);
            return;
        }
        Set<ConstraintViolation<Customer>> errors = validator.validate(customer);
        if (errors.isEmpty()) {
            writeJson(JsonUtil.serializeBean(trekkService.update(customer)), response, null);
        } else {
            writeJson(JsonUtil.serializeBean(convert(errors)), response, 400);
        }
    }

    /**
     * Delete. HTTP DELETE.
     */
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public void customerDelete(@PathVariable("id") Long id,
            HttpServletResponse response) throws IOException {
        log.debug("customerDelete({})", id);
        trekkService.delete(id);
    }

    /** Navigation sub-resource **/

    /**
     * List. HTTP GET.
     */
    @RequestMapping(value="/{id}/navigation", method=RequestMethod.GET)
    public void navigationGet(@PathVariable Long id,
            HttpServletResponse response) throws IOException {
        log.debug("navigationGet({})", id);
        writeJson(JsonUtil.serialize(trekkService.findByCustomerId(id),
                Views.Public.class), response, null);
    }

    public static void writeJson(final String json, final HttpServletResponse response,
            final Integer status) throws IOException {
        response.setContentType(APP_JSON);
        response.setCharacterEncoding("UTF-8");
        if (status != null) response.setStatus(status);
        PrintWriter out = response.getWriter();
        out.write(json);
        out.close();
    }

    private List<String> convert(Set<ConstraintViolation<Customer>> errors) {
        ImmutableList.Builder<String> b = ImmutableList.builder();
        for (ConstraintViolation<Customer> c: errors) {
            b.add(c.getPropertyPath() + " " + c.getMessage());
        }
        return b.build();
    }
}

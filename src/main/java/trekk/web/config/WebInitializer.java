package trekk.web.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionTrackingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import trekk.server.config.PropConfig;

import com.google.common.collect.ImmutableSet;

public class WebInitializer implements WebApplicationInitializer {

    private static final Logger log = LoggerFactory.getLogger(WebInitializer.class);

    public void onStartup(ServletContext servletContext) {
        log.debug("onStartup");

        // session config
        servletContext.setSessionTrackingModes(ImmutableSet.of(SessionTrackingMode.COOKIE));
        servletContext.getSessionCookieConfig().setMaxAge(1000000);

        // create the spring context
        AnnotationConfigWebApplicationContext appContext =
                new AnnotationConfigWebApplicationContext();
        appContext.register(WebConfig.class);

        // setup the environment
        PropConfig.initialize(appContext);

        // manage the lifecycle of the root application context
        servletContext.addListener(new ContextLoaderListener(appContext));

        final FilterRegistration charEncodingfilterReg =
                servletContext.addFilter("CharacterEncodingFilter",
                        CharacterEncodingFilter.class);
        charEncodingfilterReg.setInitParameter("encoding", "UTF-8");
        charEncodingfilterReg.setInitParameter("forceEncoding", "true");
        charEncodingfilterReg.addMappingForUrlPatterns(null, false, "/*");

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
                new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}

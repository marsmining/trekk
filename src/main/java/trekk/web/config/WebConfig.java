package trekk.web.config;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan({"trekk.server.config", "trekk.web.ctrl", "trekk.server.io"})
public class WebConfig extends WebMvcConfigurationSupport {

    private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

    private @Inject Environment env;

    public WebConfig() {
        log.debug("WebConfig");
    }

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
        handlerMapping.setUseSuffixPatternMatch(false);
        return handlerMapping;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        int oneYear = 60 * 60 * 24 * 365;
        String prefix = "/resources-" + env.getProperty("project.version");
        registry.addResourceHandler(prefix + "/css/**")
                .addResourceLocations("/WEB-INF/resources/css/")
                .setCachePeriod(oneYear);
        registry.addResourceHandler(prefix + "/img/**")
                .addResourceLocations("/WEB-INF/resources/img/")
                .setCachePeriod(oneYear);
        registry.addResourceHandler(prefix + "/js/**")
                .addResourceLocations("/WEB-INF/resources/js/")
                .setCachePeriod(oneYear);
        registry.addResourceHandler(prefix + "/font/**")
                .addResourceLocations("/WEB-INF/resources/font/")
                .setCachePeriod(oneYear);
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebContentInterceptor wci = new WebContentInterceptor();
        wci.setCacheSeconds(0);
        wci.setUseExpiresHeader(true);
        wci.setUseCacheControlHeader(true);
        wci.setUseCacheControlNoStore(true);
        registry.addInterceptor(wci).
            addPathPatterns("/**").
            excludePathPatterns("/resources-**");
    }
}

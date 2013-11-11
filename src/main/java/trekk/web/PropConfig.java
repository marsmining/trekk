package trekk.web;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Spring context Java configuration class which handles properties.
 *
 * <p>In the Spring context, property sources need to be added in two
 * distinct sources, one is for placeholder replacement, and the
 * second source is for the Spring
 * {@link org.springframework.core.env.Environment Environment}
 * abstraction.
 *
 * <p>The
 * {@link org.springframework.context.annotation.PropertySource PropertySource}
 * annotation loads sources for the latter, and the
 * {@link PropertySourcesPlaceholderConfigurer}
 * is for the former. However, we manually configure the latter during
 * the loading of the context. For webapps, this can be done via a
 * {@link org.springframework.web.WebApplicationInitializer WebApplicationInitializer}.
 * For tests this can be done with the {@code loader} attribute of the
 * {@link org.springframework.test.context.ContextConfiguration ContextConfiguration}
 * annotation from spring test, using something like the {@code TestContextLoader}
 * in the test package of this project.
 *
 * <p>In all cases, a call to {@link #initialize(GenericApplicationContext)}
 * should set things properly, provided it's called <i>before</i> refresh
 * is called on the context.
 */
@Configuration
public class PropConfig {

    private static final Logger log = LoggerFactory.getLogger(PropConfig.class);

    public PropConfig() {
        log.debug("PropConfig");
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer(Environment env) {
        final String root = env.getRequiredProperty("envbase");
        log.debug("envbase for placeholder: {}", root);
        PropertySourcesPlaceholderConfigurer pc = new PropertySourcesPlaceholderConfigurer();
        Resource[] locations = createResources(root);
        pc.setLocations(locations);
        return pc;
    }

    public static Properties applicationProperties(String root) {
        PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
        Resource[] locations = PropConfig.createResources(root);
        factoryBean.setLocations(locations);
        try {
            factoryBean.afterPropertiesSet();
            return factoryBean.getObject();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Resource[] createResources(String root) {
        return new Resource[] {
                new ClassPathResource("env/common/trekk.properties"),
                new ClassPathResource("env/" + root + "/trekk.properties"),
                new ClassPathResource("override.properties")
        };
    }

    public static void initialize(ConfigurableApplicationContext ctx) {
        initialize(ctx, null);
    }

    public static void initialize(ConfigurableApplicationContext ctx, Map<String, String> overrides) {
        String envbase = ctx.getEnvironment().getRequiredProperty("envbase");
        log.debug("Initializing [" + envbase + "] environment");
        MutablePropertySources ps = ctx.getEnvironment().getPropertySources();
        ps.addFirst(new PropertiesPropertySource("properties", applicationProperties(envbase)));
        if (overrides != null) {
            Properties p = new Properties();
            p.putAll(overrides);
            ps.addFirst(new PropertiesPropertySource("overrides", p));
        }
    }
}

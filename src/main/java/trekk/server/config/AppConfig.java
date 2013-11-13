package trekk.server.config;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import trekk.server.model.EntityMarker;
import trekk.server.repo.RepoMarker;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses=RepoMarker.class)
public class AppConfig {

    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    private @Inject Environment env;

    public AppConfig() {
        log.debug("AppConfig");
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setBasename("messages");
        return ms;
    }

    @Bean
    public DataSource dataSource() {
        return build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        log.debug("entityManagerFactory");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(EntityMarker.class.getPackage().getName());
        factory.setDataSource(dataSource());
        factory.setJpaDialect(new HibernateJpaDialect());
        factory.setPersistenceUnitName("trekk-pu");

        final Map<String, String> pmap = new HashMap<>();
        pmap.put("hibernate.default_schema", "trekk");
        pmap.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        pmap.put("hibernate.generate_statistics", "true");
        pmap.put("hibernate.cache.use_structured_entries", "false");
        pmap.put("hibernate.cache.use_second_level_cache", "false");
        pmap.put("hibernate.cache.use_query_cache", "false");
        factory.setJpaPropertyMap(pmap);

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }

    private ComboPooledDataSource build() {
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setDriverClass(env.getRequiredProperty("db.driver"));
            ds.setJdbcUrl(env.getRequiredProperty("db.url"));
            ds.setUser(env.getRequiredProperty("db.username"));
            ds.setPassword(env.getRequiredProperty("db.password"));
            ds.setMinPoolSize(5);
            ds.setMaxPoolSize(20);
            ds.setMaxIdleTime(3600);
            ds.setIdleConnectionTestPeriod(1800);
            return ds;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
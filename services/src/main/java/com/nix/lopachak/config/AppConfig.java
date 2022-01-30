package com.nix.lopachak.config;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

/**
 * Class содержит методы для конфигурации messageSource и SessionFactoryBean
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan({"com.nix.lopachak.service.impl", "com.nix.lopachak.dao.impl"})
public class AppConfig {
    public static final Logger LOGGER = Logger.getLogger(AppConfig.class.getName());

    @Autowired
    private Environment env;

    /**
     * Метод зарружает ресурсы с db.properties
     *
     * @return -  source - interface MessageSource
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        LOGGER.info("MessageSource BEAN OK");
        return source;
    }

    /**
     * Метод создает factoryBean
     * @return - сконфигурированный factoryBean
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        Properties props = new Properties();
        // Setting JDBC properties
        props.put(DRIVER, env.getProperty("db.driver"));
        final String dbUrl = System.getenv("DB_URL");
        if (dbUrl != null) {
            props.put(URL, dbUrl);
        } else{
            props.put(URL, env.getProperty("db.url"));
        }
        props.put(USER, env.getProperty("db.username"));
        props.put(PASS, env.getProperty("db.password"));
        props.put(DIALECT, env.getProperty("hibernate.dialect"));

        // Setting C3P0 properties
        props.put(C3P0_MIN_SIZE,
                env.getProperty("hibernate.c3p0.min_size"));
        props.put(C3P0_MAX_SIZE,
                env.getProperty("hibernate.c3p0.max_size"));
        props.put(C3P0_ACQUIRE_INCREMENT,
                env.getProperty("hibernate.c3p0.acquire_increment"));
        props.put(C3P0_TIMEOUT,
                env.getProperty("hibernate.c3p0.timeout"));
        props.put(C3P0_MAX_STATEMENTS,
                env.getProperty("hibernate.c3p0.max_statements"));

        factoryBean.setHibernateProperties(props);
        factoryBean.setPackagesToScan("com.nix.lopachak.entity");
        LOGGER.info("CREATE LocalSessionFactoryBean OK !!!");
        return factoryBean;
    }

    /**
     * Метод создаёт и конфигурирует transactionManager
     *
     * @return - сконфигурированный transactionManager
     */
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        LOGGER.info("HibernateTransactionManager BEAN create OK !!!");
        return transactionManager;
    }
}

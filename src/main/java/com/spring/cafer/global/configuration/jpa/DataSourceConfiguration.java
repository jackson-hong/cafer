package com.spring.cafer.global.configuration.jpa;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DataSourceConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

    private final DataSource dataSource;
    private final Environment env;

    public DataSourceConfiguration(DataSource dataSource, Environment env) {
        this.dataSource = dataSource;
        this.env = env;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logDatabaseConnection() {
        try (var connection = dataSource.getConnection()) {
            if (connection.isValid(1)) {
                logger.info("Successfully connected to the database: " + env.getProperty("spring.datasource.url"));
            } else {
                logger.error("Failed to validate database connection.");
            }
        } catch (Exception e) {
            logger.error("Error while checking database connection: ", e);
        }
    }
}


/*
 * Syed Ammar Waseem
 * Assignment 3
 * SDNE(Software Development Network Engineering)
 *
 * Database Config
 *
 */
package ca.ammar.website.application.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;


// configuration for the database access
@Configuration
public class DatabaseConfig {

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);

    }

}

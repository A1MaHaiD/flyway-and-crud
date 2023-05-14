package com.goit.model.config;

import com.goit.model.util.Constants;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.internal.jdbc.DriverDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Driver;
import java.util.Properties;

public class FlywayConfigurations {
    private static final String DEFAULT_FILE_NAME = "application.properties";
    Flyway flyway;

    public FlywayConfigurations setup() throws IOException {
        setup(DEFAULT_FILE_NAME);
        return this;
    }

    public FlywayConfigurations setup(String propertiesFileName) throws IOException {
        Properties properties = new Properties();
        properties.load(FlywayConfigurations.class.getClassLoader().getResourceAsStream(propertiesFileName));
        String url = properties.getProperty(Constants.FLYWAY_CONNECTION_URL);
        String username = properties.getProperty(Constants.FLYWAY_USER);
        String password = properties.getProperty(Constants.FLYWAY_PASSWORD);
        Location migration = new Location("db/migrate");
        Location mixture = new Location("db/mixture");

        DataSource dataSource = new DriverDataSource(
                FlywayConfigurations.class.getClassLoader(),
                Driver.class.getName(),
                url,
                username,
                password
        );
        flyway = Flyway.configure()
                .encoding(StandardCharsets.UTF_8)
                .locations(migration, mixture)
                .dataSource(dataSource)
                .placeholderReplacement(false)
                .failOnMissingLocations(true)
                .load();
        return this;
    }

    public void migration() {
        flyway.migrate();
    }
}
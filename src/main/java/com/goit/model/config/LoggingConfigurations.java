package com.goit.model.config;

import com.goit.model.exception.DbException;
import org.apache.log4j.*;

import java.util.Properties;

import static com.goit.model.util.Constants.*;

public class LoggingConfigurations {
    private static final String DEFAULT_FILE_NAME = "application.properties";
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(LoggingConfigurations.class.getResourceAsStream(DEFAULT_FILE_NAME));
        } catch (Exception e) {
            throw new DbException("LoggingConfigurations", e);
        }
        // create pattern layout
        PatternLayout patternLayout = new PatternLayout();
        patternLayout.setConversionPattern(properties.getProperty(LOG_PATTERN));

        // creates console appender
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setLayout(patternLayout);
        consoleAppender.setEncoding(properties.getProperty(LOG_ENCODING));
        consoleAppender.activateOptions();

        // create file appender
        DailyRollingFileAppender rollingFileAppender = new DailyRollingFileAppender();
        rollingFileAppender.setEncoding(properties.getProperty(LOG_ENCODING));
        rollingFileAppender.setFile(properties.getProperty(LOG_FILE));
        rollingFileAppender.setLayout(patternLayout);
        rollingFileAppender.setDatePattern("");
        rollingFileAppender.activateOptions();

        // configures the root logger
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.toLevel(properties.getProperty(LOG_LEVEL)));
        rootLogger.removeAllAppenders();
        rootLogger.addAppender(consoleAppender);
        rootLogger.addAppender(rollingFileAppender);
    }
}

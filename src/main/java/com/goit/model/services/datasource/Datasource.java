package com.goit.model.services.datasource;

import com.goit.model.exception.DbException;
import com.goit.model.util.Constants;
import com.mysql.cj.jdbc.NonRegisteringDriver;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

import static com.goit.model.util.Constants.CONNECTION_AUTOCOMMIT;
import static com.goit.model.util.Constants.TRANSACTION_ISOLATION;


public class Datasource {
    private Connection connection;
    private static final String DEFAULT_FILE_NAME = "application.properties";
    private Properties properties = new Properties();

    public Datasource(String propertiesFileName) {
        try {
            properties.load(Datasource.class.getClassLoader().getResourceAsStream(propertiesFileName));
        } catch (Exception e) {
            throw new DbException("Open connection failed", e);
        }
    }

    public Datasource() {
        try {
            properties.load(Datasource.class.getClassLoader().getResourceAsStream(DEFAULT_FILE_NAME));
        } catch (Exception e) {
            throw new DbException("Open connection failed", e);
        }
    }

    public void openConnection() {
        try {
            NonRegisteringDriver driver = new NonRegisteringDriver();
            DriverManager.registerDriver(driver);
            String url = properties.getProperty(Constants.CONNECTION_URL);
            String username = properties.getProperty(Constants.CONNECTION_USERNAME);
            String password = properties.getProperty(Constants.CONNECTION_PASSWORD);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new DbException("Open connection failed ", e);
        }
    }

    public PreparedStatement preparedStatement(@Language("SQL") String query) {
        try {
            openConnection();
            connection.createStatement().execute(getIsolationLevelQuery(properties.getProperty(TRANSACTION_ISOLATION)));
            boolean autocommit = Boolean.parseBoolean(properties.getProperty(CONNECTION_AUTOCOMMIT));
            connection.setAutoCommit(autocommit);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement;
        } catch (Exception e) {
            throw new DbException("Can't create prepared statement", e);
        }
    }

    public PreparedStatement preparedStatement(@Language("SQL") String query, boolean autocommit) {
        try {
            openConnection();
            connection.createStatement().execute(getIsolationLevelQuery(properties.getProperty(TRANSACTION_ISOLATION)));
            connection.setAutoCommit(autocommit);
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            return preparedStatement;
        } catch (Exception e) {
            throw new DbException("Can't create prepared statement", e);
        }
    }

    private String getIsolationLevelQuery(String name) {
        switch (name) {
            case "READ_UNCOMMITTED":
                return "SET GLOBAL TRANSACTION ISOLATION LEVEL READ UNCOMMITTED";
            case "REPEATABLE_READ":
                return "SET GLOBAL TRANSACTION ISOLATION LEVEL REPEATABLE READ";
            case "READ_COMMITTED":
                return "SET GLOBAL TRANSACTION ISOLATION LEVEL READ COMMITTED";
            case "SERIALIZABLE":
                return "SET GLOBAL TRANSACTION ISOLATION LEVEL SERIALIZABLE";
            default:
                throw new DbException("Unsupported isolation level:" + name);
        }
    }

    public void commit() {
        try {
            if (!connection.isClosed()) {
                connection.commit();
            }
        } catch (Exception e) {
            throw new DbException("Transaction commit failed", e);
        }
    }

    public void close() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            throw new DbException("Connection close failed", e);
        }
    }

    public void rollback() {
        try {
            if (!connection.isClosed()) {
                connection.rollback();
                connection.close();
            }
        } catch (Exception e) {
            throw new DbException("Transaction commit failed", e);
        }
    }
}

package com.goit.model.db;

import com.goit.model.exception.DbException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String H2_URL = "jdbc:h2:./local";
    private static final String USER = "admin";
    private static final String PASSWORD = "12345";
    private static Database instance;
    private Connection connection;
    private Database() {
        try {
            connection = DriverManager.getConnection(H2_URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DbException("Instantiation of database failed", e);
        }
    }
    public static synchronized Database getInstance() {
        try {
            return new Database();
        } catch (Exception e) {
            throw new DbException("Instantiation of Database failed", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}

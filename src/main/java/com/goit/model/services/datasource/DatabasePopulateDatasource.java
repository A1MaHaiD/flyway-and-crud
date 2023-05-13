package com.goit.model.services.datasource;

import com.goit.model.exception.DbException;
import com.mysql.cj.jdbc.Driver;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

@RequiredArgsConstructor(staticName = "of")
public class DatabasePopulateDatasource {
    private final String mysqlUrl;
    private final String username;
    private final String password;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public PreparedStatement prepareStatement(@Language("SQL") String query) {
        try {
            preparedStatement = openConnection().prepareStatement(query);
            return preparedStatement;
        } catch (Exception e) {
            throw new DbException("Can't create prepared statement", e);
        }
    }

    private Connection openConnection() {
        try {
            DriverManager.registerDriver(new Driver() {
            });
            return DriverManager.getConnection(mysqlUrl, username, password);
        } catch (Exception e) {
            throw new DbException("Open connection failed ", e);
        }
    }
}

package com.goit.model.services.database_service;

import com.goit.model.db.Database;
import com.goit.model.exception.DbException;
import com.goit.model.reader.Reader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitService {
    public static void main(String[] args) throws SQLException {
        Reader sb = new Reader("migrate/init_db.sql");
        Connection conn = Database.getInstance().getConnection();
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sb.read());
            stmt.close();
            conn.close();
        } catch (Exception e) {
            throw new DbException("Connection or statement failed",e);
        }
    }
}
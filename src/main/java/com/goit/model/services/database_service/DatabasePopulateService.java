package com.goit.model.services.database_service;

import com.goit.model.services.database_service.database_populate.DatabasePopulateProjectWorker;
import com.goit.model.services.database_service.database_populate.DatabasePopulateClient;
import com.goit.model.services.database_service.database_populate.DatabasePopulateProject;
import com.goit.model.services.database_service.database_populate.DatabasePopulateWorker;

public class DatabasePopulateService {
    private static final String MYH2_URL = "jdbc:h2:~/test";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "12345";
    public static void main(String[] args) {
        DatabasePopulateWorker databasePopulateWorker = new DatabasePopulateWorker();
        databasePopulateWorker.workerInsert();
        DatabasePopulateClient databasePopulateClient = new DatabasePopulateClient();
        databasePopulateClient.clientInsert();
        DatabasePopulateProject databasePopulateProject = new DatabasePopulateProject();
        databasePopulateProject.projectInsert();
        DatabasePopulateProjectWorker databasePopulateProjectWorker = new DatabasePopulateProjectWorker();
        databasePopulateProjectWorker.projectWorkerInsert();


 /*       Reader sb = new Reader("sql/populate_db.sql");
        DatabasePopulateDatasource data = DatabasePopulateDatasource.of(MYH2_URL, USERNAME, PASSWORD);
        Connection conn = Database.getInstance().getConnection();
        try {
            Statement stmt = conn.prepareStatement(sb.read());
            stmt.execute(sb.read());
            stmt.close();
            conn.close();
            PreparedStatement preparedStatement = data.prepareStatement(sb.read());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement.close();
        } catch (Exception e) {
            throw new DbException("Connection or statement failed", e);
        }*/
    }
}
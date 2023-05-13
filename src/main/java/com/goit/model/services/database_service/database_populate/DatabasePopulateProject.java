package com.goit.model.services.database_service.database_populate;

import com.goit.model.db.Database;
import com.goit.model.db.tables.Project;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.Files.readString;

public class DatabasePopulateProject {
    PreparedStatement preparedStatement;
    Connection connection;

    public void projectInsert() {
        List<Project> projectList = addProjectInfo();
        try {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO project (client_id, start_date, finish_date) VALUES (?, ?, ?)"
            );
            for (Project project : projectList) {
                preparedStatement.setInt(1, project.getClientId());
                preparedStatement.setDate(2, project.getStartDate());
                preparedStatement.setDate(3, project.getFinishDate());
                preparedStatement.executeUpdate();
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String file) {
        String fileValue = null;
        try {
            fileValue = readString(Path.of(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileValue;
    }

    public List<Project> addProjectInfo() {
        List<Project> projectList = new ArrayList<>();
        String line = readFile("project.txt");
        Scanner sc = new Scanner(line);
        while (sc.hasNextLine()) {
            String[] query = sc.nextLine().split(",");
            Project project = new Project();
            project.setClientId(Integer.parseInt(query[0]));
            project.setStartDate(Date.valueOf(query[1]));
            project.setFinishDate(Date.valueOf(query[2]));
            projectList.add(project);
        }
        return projectList;
    }
}

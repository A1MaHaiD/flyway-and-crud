package com.goit.model.services.database_service.database_populate;

import com.goit.model.db.Database;
import com.goit.model.db.tables.ProjectWorker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabasePopulateProjectWorker {
    PreparedStatement preparedStatement;
    Connection connection;

    public void projectWorkerInsert(){
        List<ProjectWorker> projectWorkerList = addProjectWorkerInfo();
        try {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO project_worker (worker_id, project_id) VALUES (?, ?)"
            );
            for (ProjectWorker projectWorker : projectWorkerList){
                preparedStatement.setInt(1, projectWorker.getWorkerId());
                preparedStatement.setInt(2, projectWorker.getProjectId());
                preparedStatement.executeUpdate();
            }
            preparedStatement.close();
            connection.close();
        } catch ( SQLException e){
            e.printStackTrace();
        }
    }

    public String readFile(String file) {
        String fileValue = null;
        try {
            fileValue = Files.readString(Path.of(file));
        } catch (IOException e){
            e.printStackTrace();
        }
        return  fileValue;
    }

    public List<ProjectWorker> addProjectWorkerInfo() {
        List<ProjectWorker> projectWorkerList = new ArrayList<>();
        String line = readFile("project_worker.txt");
        Scanner sc = new Scanner(line);
        while (sc.hasNextLine()){
            String[] query = sc.nextLine().split(",");
            ProjectWorker projectWorker = new ProjectWorker();
            projectWorker.setWorkerId(Integer.parseInt(query[0]));
            projectWorker.setProjectId(Integer.parseInt(query[1]));
            projectWorkerList.add(projectWorker);
        }
        return projectWorkerList;
    }
}

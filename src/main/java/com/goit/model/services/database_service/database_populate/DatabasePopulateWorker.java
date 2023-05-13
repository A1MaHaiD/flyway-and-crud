package com.goit.model.services.database_service.database_populate;

import com.goit.model.db.Database;
import com.goit.model.db.tables.Worker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabasePopulateWorker {
    PreparedStatement preparedStatement;
    Connection connection;

    public void workerInsert() {
        List<Worker> workerList = addWorkerInfo();
        try {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO worker (name, birthday, level, salary) VALUES (?, ?, ?, ?)");
            for (Worker worker : workerList) {
                preparedStatement.setString(1, worker.getName());
                preparedStatement.setDate(2, worker.getBirthday());
                preparedStatement.setString(3, worker.getLevel());
                preparedStatement.setInt(4, worker.getSalary());
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
            fileValue = Files.readString(Path.of(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileValue;
    }

    public List<Worker> addWorkerInfo() {
        List<Worker> workerList = new ArrayList<>();
        String line = readFile("worker.txt");
        Scanner sc = new Scanner(line);
        while (sc.hasNextLine()) {
            String[] query = sc.nextLine().split(",");
            Worker worker = new Worker();
            worker.setName(query[0]);
            worker.setBirthday(Date.valueOf(query[1]));
            worker.setLevel(query[2]);
            worker.setSalary(Integer.parseInt(query[3]));
            workerList.add(worker);
        }
        return workerList;
    }
}
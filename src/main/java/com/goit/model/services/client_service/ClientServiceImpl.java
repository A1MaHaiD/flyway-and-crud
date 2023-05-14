package com.goit.model.services.client_service;

import com.goit.model.db.tables.Client;
import com.goit.model.exception.DbException;
import com.goit.model.services.datasource.Datasource;
import lombok.extern.log4j.Log4j;
import org.intellij.lang.annotations.Language;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class ClientServiceImpl extends ClientService<Client> {

    @Language("SQL")
    private final String INSERT_CLIENT = """
            INSERT INTO client (id, name) VALUES (DEFAULT, ?);
            """;
    @Language("SQL")
    private final String SET_NAME_CLIENT = """
            UPDATE client SET name=? WHERE id=?;
            """;

    public ClientServiceImpl(Datasource datasource) {
        super(datasource);
        log.info("Created ClientServiceImpl");
    }

    @Override
    public long create(String name) {
        try {
            Client client = new Client();
            PreparedStatement preparedStatement = datasource.preparedStatement(INSERT_CLIENT);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.first();
            long clientId = generatedKeys.getLong("id");
            client.setId(clientId);
            datasource.close();
            return clientId;
        } catch (Exception e) {
            throw new DbException("", e);
        }
    }

    @Override
    public String getById(long id) {
        try {
            PreparedStatement preparedStatement = datasource.preparedStatement(FIND_BY_ID_QUERY, true);
            preparedStatement.setString(1, "id");
            preparedStatement.setLong(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean next = resultSet.next();
            if (!next) {
                return null;
            }
            Client client = parseClientRow(resultSet);
            datasource.close();
            return String.valueOf(client.getId());
        } catch (Exception e) {
            log.error(e);
            throw new DbException("getById", e);
        }
    }

    @Override
    public void setName(long id, String name) {
        try {
            PreparedStatement preparedStatement = datasource.preparedStatement(SET_NAME_CLIENT, true);
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            datasource.close();
        } catch (Exception e) {
            log.error(e);
            throw new DbException("setName", e);
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            PreparedStatement preparedStatement = datasource.preparedStatement(DELETE_BY_ID_QUERY);
            preparedStatement.setString(1, "id");
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            datasource.close();
        } catch (Exception e) {
            log.error(e);
            throw new DbException("deleteById", e);
        }
    }

    @Override
    public List<Client> listAll() {
        log.info("Loading all clients");
        try {
            PreparedStatement preparedStatement = datasource.preparedStatement(FIND_ALL_QUERY, true);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Client> clientList = new ArrayList<>();
            while (resultSet.next()) {
                Client client = parseClientRow(resultSet);
                clientList.add(client);
            }
            datasource.close();
            log.info("All client loaded");
            return clientList;
        } catch (Exception e) {
            log.error(e);
            throw new DbException("listAll", e);
        }
    }

    private Client parseClientRow(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return Client.of(id, name);
    }
}

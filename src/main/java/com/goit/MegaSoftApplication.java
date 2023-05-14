package com.goit;

import com.goit.model.config.FlywayConfigurations;
import com.goit.model.config.LoggingConfigurations;
import com.goit.model.db.tables.Client;
import com.goit.model.services.client_service.ClientService;
import com.goit.model.services.client_service.ClientServiceImpl;
import com.goit.model.services.datasource.Datasource;

import java.io.IOException;
import java.util.List;

public class MegaSoftApplication {
    public static void main(String[] args) throws IOException {
        new LoggingConfigurations();
        new FlywayConfigurations().setup().migration();
        ClientService<Client> service = new ClientServiceImpl(new Datasource());
        List<Client> clientList = service.listAll();
        System.out.println(clientList);
    }
}
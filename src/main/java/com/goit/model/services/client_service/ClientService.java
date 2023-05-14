package com.goit.model.services.client_service;

import com.goit.model.services.datasource.Datasource;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;

import java.util.List;
@RequiredArgsConstructor
public abstract class ClientService<T> {
    @Language("SQL")
    protected final String FIND_BY_ID_QUERY = "SELECT * FROM client WHERE id=?;";
    @Language("SQL")
    protected final String FIND_ALL_QUERY = "SELECT * FROM client;";
    @Language("SQL")
    protected final String DELETE_BY_ID_QUERY = "DELETE FROM client WHERE id=?;";
    protected final Datasource datasource;

    public abstract long create(String name);

    public abstract String getById(long id);

    public abstract void setName(long id, String name);

    public abstract void deleteById(long id);

    public abstract List<T> listAll();
}

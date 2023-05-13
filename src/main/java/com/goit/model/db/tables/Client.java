package com.goit.model.db.tables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Client {
    int id;
    String name;

    public Client() {

    }
}

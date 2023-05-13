package com.goit.model.db.tables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class Project {
    int clientId;
    Date startDate;
    Date finishDate;

    public Project() {

    }
}

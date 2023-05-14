package com.goit.model.db.tables;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Worker {
    String name;
    Date birthday;
    String level;
    int salary;
}
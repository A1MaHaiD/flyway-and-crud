package com.goit.model.db.tables;

import lombok.*;

import java.sql.Date;


@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Project {
    long clientId;
    Date startDate;
    Date finishDate;
}

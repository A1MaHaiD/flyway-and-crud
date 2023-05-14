package com.goit.model.db.tables;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ProjectWorker {
    int workerId;
    int projectId;
}

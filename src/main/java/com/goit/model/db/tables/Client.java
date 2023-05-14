package com.goit.model.db.tables;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor
@ToString
public class Client {
    long id;
    String name;
}

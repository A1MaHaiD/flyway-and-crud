create TABLE IF NOT EXISTS worker
(
    id int auto_increment,
    name varchar(1000) NOT NULL CHECK(length(name) >= 2),
    birthday date NOT NULL CHECK (birthday > '1900-01-01'),
    level varchar(10) NOT NULL CHECK (level IN ('Trainee','Junior','Middle','Senior')),
    salary int CHECK (salary BETWEEN 100 AND 100000),
    PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS client
(
    id int auto_increment,
    name varchar(1000) NOT NULL CHECK (length(name) >=2),
    PRIMARY KEY(id)
);

create TABLE IF NOT EXISTS project
(
    id int auto_increment PRIMARY KEY,
    client_id int,
    start_date date,
    finish_date date CHECK (finish_date >= start_date),
    FOREIGN KEY(client_id) REFERENCES client(id)
);

create TABLE IF NOT EXISTS project_worker
(
    worker_id int NOT NULL REFERENCES worker(id),
    project_id int NOT NULL REFERENCES project(id),
    PRIMARY KEY (worker_id,project_id)
);
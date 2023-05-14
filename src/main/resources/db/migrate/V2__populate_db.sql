insert into worker(name,birthday,level,salary)
values
('Andrew', '1990-01-01', 'Senior', 8000),
('Elena', '1995-05-15', 'Middle', 5000),
('Ivan', '2000-10-31', 'Junior', 3000),
('Mary', '1998-07-07', 'Middle', 4500),
('Peter', '1999-12-25', 'Junior', 2500),
('Anna', '1997-03-03', 'Middle', 4000),
('Sergey', '1996-06-06', 'Senior', 7500),
('Julia', '2001-09-09', 'Trainee', 1500),
('Oleksandr', '1994-04-04', 'Senior', 8500),
('Natalia', '2002-08-08', 'Trainee', 1200);

insert into client(name)
values
('Google'),
('Armed Forces Ukraine'),
('Facebook'),
('GOIT'),
('Microsoft');

insert into project(client_id,start_date,finish_date)
values
(1, '2020-01-01', '2021-01-01'),
(2, '2020-02-01', '2021-04-01'),
(3, '2020-03-01', '2022-03-01'),
(4, '2020-04-01', '2021-06-01'),
(5, '2020-05-01', '2022-05-01');

insert into project_worker(worker_id ,project_id)
values
(1 , 1),
(2 , 1),
(3 , 2),
(4 , 2),
(5 , 3),
(6 , 3),
(7 , 4),
(8 , 4),
(9 , 5),
(10 , 5);
insert into user_app (create_at, delete_at, update_at, company, email, name, password, role, status,id) values ('2020-01-03', null, null, 'Nisum', 'calvarez@nisum.com', 'Carlos Alvarez', '$2a$10$idhhARL540ABza68wtOXBuZcpk84Z2ckaLhFBBAY8ONStbOFDYkw.', true, true, 1);
insert into user_app (create_at, delete_at, update_at, company, email, name, password, role, status,id) values ('2020-01-03', null, null, 'Nisum', 'sriquelme@nisum.com', 'Sebastian Riquelme', '$2a$10$idhhARL540ABza68wtOXBuZcpk84Z2ckaLhFBBAY8ONStbOFDYkw.', true, true, 2);
insert into user_app (create_at, delete_at, update_at, company, email, name, password, role, status,id) values ('2020-01-03', null, null, 'Nisum', 'jdominguez@nisum.com', 'Juan Dominguez', '$2a$10$idhhARL540ABza68wtOXBuZcpk84Z2ckaLhFBBAY8ONStbOFDYkw.', true, true, 3);
insert into user_app (create_at, delete_at, update_at, company, email, name, password, role, status,id) values ('2020-01-03', null, null, 'Nisum', 'csalinas@nisum.com', 'Camilo Salinas Riquelme', '$2a$10$idhhARL540ABza68wtOXBuZcpk84Z2ckaLhFBBAY8ONStbOFDYkw.', true, true, 4);
insert into user_app (create_at, delete_at, update_at, company, email, name, password, role, status,id) values ('2020-01-03', null, null, 'Nisum', 'dpalma@nisum.com', 'Diego Palma', '$2a$10$idhhARL540ABza68wtOXBuZcpk84Z2ckaLhFBBAY8ONStbOFDYkw.', true, true, 5);

insert into value (create_at, delete_at, update_at, name, description, id) values ('2020-01-03', null, null, 'Play', 'Our Play', 1);
insert into value (create_at, delete_at, update_at, name, description, id) values ('2020-01-03', null, null, 'Respect', 'Our Respect', 2);
insert into value (create_at, delete_at, update_at, name, description, id) values ('2020-01-03', null, null, 'Openess', 'Our Openess', 3);
insert into value (create_at, delete_at, update_at, name, description, id) values ('2020-01-03', null, null, 'Customer success', 'Our Customer', 4);
insert into value (create_at, delete_at, update_at, name, description, id) values ('2020-01-03', null, null, 'Excellence', 'Our Excellence', 5);

insert into nomination (create_at, delete_at, update_at, description, nominator_id, user_id, value_id, id) values ('2020-01-03', null, null,'Good Player', 1, 2, 1, 1);
insert into nomination (create_at, delete_at, update_at, description, nominator_id, user_id, value_id, id) values ('2020-01-03', null, null,'Respect!', 2, 5, 2, 2);
insert into nomination (create_at, delete_at, update_at, description, nominator_id, user_id, value_id, id) values ('2020-01-03', null, null,'Openess!', 3, 4, 3, 3);
insert into nomination (create_at, delete_at, update_at, description, nominator_id, user_id, value_id, id) values ('2020-01-03', null, null,'Customer succes!', 4, 1, 4, 4);
insert into nomination (create_at, delete_at, update_at, description, nominator_id, user_id, value_id, id) values ('2020-01-03', null, null,'Excellence!', 5, 2, 5, 5);
insert into nomination (create_at, delete_at, update_at, description, nominator_id, user_id, value_id, id) values ('2020-01-03', null, null,'Excellence!!!!', 3, 2, 5, 6);
insert into nomination (create_at, delete_at, update_at, description, nominator_id, user_id, value_id, id) values ('2020-01-03', null, null,'Good Player!!', 2, 1, 1, 7);
insert into nomination (create_at, delete_at, update_at, description, nominator_id, user_id, value_id, id) values ('2020-01-03', null, null,'Good Player!!', 1, 2, 1, 8);


insert into campaign (create_at, delete_at, update_at, date_from, date_to, description, status, id) values ('2020-01-03', null, null, '2020-01-01', '2020-03-30', 'First Campaign', true, 1);
insert into campaign (create_at, delete_at, update_at, date_from, date_to, description, status, id) values ('2020-01-03', null, null, '2020-04-01', '2020-06-30', '2nd Campaign', true, 2);
insert into campaign (create_at, delete_at, update_at, date_from, date_to, description, status, id) values ('2020-01-03', null, null, '2020-07-01', '2020-10-30', '3rd Campaign', true, 3);
insert into campaign (create_at, delete_at, update_at, date_from, date_to, description, status, id) values ('2020-01-03', null, null, '2020-11-01', '2020-12-30', '4th Campaign', true, 4);
insert into campaign (create_at, delete_at, update_at, date_from, date_to, description, status, id) values ('2020-01-03', null, null, '2021-01-01', '2021-04-01', '5th Campaign', true, 5);

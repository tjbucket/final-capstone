BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('kyle','$2a$10$XkciSbxVZvNHjkzM2FDMVOqh6S8ZR7s.zSNpHS8XhrmX2bbU02owG','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('terry','$2a$10$otfNpEq8BEQnQdRDBPsrueBNUJmrBhmxvaBR8H3LExcX1zQzumiFC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('daniel','$2a$10$ofQlo3dIgHrrqZAZGBakWO7kIG5U7mzvtPZdLSmlIB5SotYKNjf46','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('john','$2a$10$XkciSbxVZvNHjkzM2FDMVOqh6S8ZR7s.zSNpHS8XhrmX2bbU02owG','ROLE_USER');


--Starting at 7 for users to be pending
INSERT INTO users (username,password_hash,role) VALUES ('jarrod','$2a$10$XkciSbxVZvNHjkzM2FDMVOqh6S8ZR7s.zSNpHS8XhrmX2bbU02owG','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('jarod','$2a$10$XkciSbxVZvNHjkzM2FDMVOqh6S8ZR7s.zSNpHS8XhrmX2bbU02owG','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('chris','$2a$10$XkciSbxVZvNHjkzM2FDMVOqh6S8ZR7s.zSNpHS8XhrmX2bbU02owG','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('rachelle','$2a$10$XkciSbxVZvNHjkzM2FDMVOqh6S8ZR7s.zSNpHS8XhrmX2bbU02owG','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('dylan','$2a$10$XkciSbxVZvNHjkzM2FDMVOqh6S8ZR7s.zSNpHS8XhrmX2bbU02owG','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('jenn','$2a$10$XkciSbxVZvNHjkzM2FDMVOqh6S8ZR7s.zSNpHS8XhrmX2bbU02owG','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('claud','$2a$10$XkciSbxVZvNHjkzM2FDMVOqh6S8ZR7s.zSNpHS8XhrmX2bbU02owG','ROLE_USER');

INSERT INTO roles (role_description) VALUES ('Tenant'),('Maintenance'),('Manager'), ('Inactive');

INSERT INTO units (unit_number) VALUES (101),(102),(103),(104),(105),(201),(202),(203),(204),(205),(301),(302),(303),(304),(305);
INSERT INTO units (unit_number, unit_description) VALUES (0, 'Deactivated'), (1, 'Residence'),(2, 'Clubhouse'),(3,'Parking Lot');


INSERT INTO persons (user_id, role_id, unit_number, first_name, last_name, email, property_comment)
VALUES (3, 3, 101, 'Kyle', 'Flinner','mainttenantsprimary@gmail.com', 'Property Manager'),
(4, 2, 201, 'Terry', 'Mayberry','mainttenantssecondary@gmail.com', 'Maintenance Supervisor'),
(5, 1, 103, 'Daniel', 'Wang','mainttenantstertiary@gmail.com', 'Dan has 3 dogs and they try to run out the door!'),
(6, 1, 302, 'John', 'Doe','mainttenantstertiary@gmail.com', 'John is old and takes a second to get to the door, call ahead to have him unlock!');

INSERT INTO persons (user_id, first_name, last_name, email)
VALUES (7, 'jarrod', 'mccoy','mainttenantstertiary@gmail.com'),
(8, 'jarod', 'dove','mainttenantstertiary@gmail.com'),
(9, 'chris', 'nice','mainttenantstertiary@gmail.com'),
(10, 'rachelle', 'rauh','mainttenantstertiary@gmail.com'),
(7, 'dylan', 'chris','mainttenantstertiary@gmail.com'),
(8, 'jenn', 'trevis','mainttenantstertiary@gmail.com'),
(9, 'claud', 'tiqui','mainttenantstertiary@gmail.com');

INSERT INTO work_areas (area_description) VALUES ('Plumbing'), ('Electrical'), ('HVAC'), ('Carpentry'), ('Appliances'), ('N/A');

INSERT INTO work_statuses (status_description) VALUES ('Open'), ('Pending'), ('Closed'), ('Accepted');


INSERT INTO work_orders (submitter_id, unit_number, area_id, status_id, order_description, when_posted, when_accepted, rating)
VALUES (3, 301, 3, 4, 'OLD', LOCALTIMESTAMP - interval '185 days', LOCALTIMESTAMP - interval '179 days', 3),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '183 days', LOCALTIMESTAMP - interval '180 days', 3),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '177 days', LOCALTIMESTAMP - interval '175 days', 3),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '170 days', LOCALTIMESTAMP - interval '168 days', 3),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '166 days', LOCALTIMESTAMP - interval '165 days', 3),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '136 days', LOCALTIMESTAMP - interval '134 days', 5),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '100 days', LOCALTIMESTAMP - interval '97 days', 5),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '90 days', LOCALTIMESTAMP - interval '85 days', 1),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '80 days', LOCALTIMESTAMP - interval '78 days', 5),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '70 days', LOCALTIMESTAMP - interval '66 days', 3),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '60 days', LOCALTIMESTAMP - interval '55 days', 4),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '50 days', LOCALTIMESTAMP - interval '49 days', 5),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '40 days', LOCALTIMESTAMP - interval '34 days', 4),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '30 days', LOCALTIMESTAMP - interval '27 days', 5),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '20 days', LOCALTIMESTAMP - interval '18 days', 4),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '10 days', LOCALTIMESTAMP - interval '4 days', 4),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '166 days', LOCALTIMESTAMP - interval '164 days', 3),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '136 days', LOCALTIMESTAMP - interval '135 days', 5),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '100 days', LOCALTIMESTAMP - interval '98 days', 5),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '90 days', LOCALTIMESTAMP - interval '89 days', 1),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '80 days', LOCALTIMESTAMP - interval '79 days', 5),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '70 days', LOCALTIMESTAMP - interval '69 days', 4),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '60 days', LOCALTIMESTAMP - interval '57 days', 5),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '50 days', LOCALTIMESTAMP - interval '46 days', 2),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '40 days', LOCALTIMESTAMP - interval '38 days', 5),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '30 days', LOCALTIMESTAMP - interval '26 days', 5),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '20 days', LOCALTIMESTAMP - interval '16 days', 4),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '10 days', LOCALTIMESTAMP - interval '8 days', 4),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '15 days', LOCALTIMESTAMP - interval '12 days', 5),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '15 days', LOCALTIMESTAMP - interval '14 days', 4),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '15 days', LOCALTIMESTAMP - interval '14 days', 5),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '14 days', LOCALTIMESTAMP - interval '11 days', 3),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '13 days', LOCALTIMESTAMP - interval '12 days', 5),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '12 days', LOCALTIMESTAMP - interval '11 days', 2),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '11 days', LOCALTIMESTAMP - interval '10 days', 3),
(3, 301, 3, 4, 'AC broken', LOCALTIMESTAMP - interval '10 days', LOCALTIMESTAMP - interval '8 days', 4);

COMMIT TRANSACTION;

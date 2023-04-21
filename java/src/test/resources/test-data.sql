BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user1','user1','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user2','user2','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user3','user3','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user4','user4','ROLE_USER');

INSERT INTO roles (role_description) VALUES ('Tenant');
INSERT INTO roles (role_description) VALUES ('Maintenance');
INSERT INTO roles (role_description) VALUES ('Manager');

INSERT INTO units (unit_number) VALUES (1001);
INSERT INTO units (unit_number) VALUES (2001);
INSERT INTO units (unit_number) VALUES (3001);

INSERT INTO persons (user_id, role_id, unit_number, first_name, last_name)
VALUES (1, 1, 1001, 'John', 'Doe');
INSERT INTO persons (user_id, role_id, unit_number, first_name, last_name)
VALUES (2, 1, 1001, 'Mary', 'Smith');
INSERT INTO persons (user_id, role_id, unit_number, first_name, last_name)
VALUES (3, 1, 2001, 'Doug', 'Brown');
INSERT INTO persons (user_id, role_id, unit_number, first_name, last_name)
VALUES (4, 2, 3001, 'Bob', 'Jones');

INSERT INTO work_areas (area_description) VALUES ('Plumbing');
INSERT INTO work_areas (area_description) VALUES ('Electrical');
INSERT INTO work_areas (area_description) VALUES ('HVAC');

INSERT INTO work_statuses (status_description) VALUES ('Open');
INSERT INTO work_statuses (status_description) VALUES ('Pending');
INSERT INTO work_statuses (status_description) VALUES ('Closed');

INSERT INTO work_orders (submitter_id, unit_number, area_id, status_id, order_description)
VALUES (1, 1001, 1, 1, 'Leaky faucet');
INSERT INTO work_orders (submitter_id, unit_number, area_id, status_id, order_description)
VALUES (2, 1001, 2, 1, 'Porch light out');
INSERT INTO work_orders (submitter_id, unit_number, area_id, status_id, order_description)
VALUES (3, 2001, 3, 2, 'Heating broken');
INSERT INTO work_orders (submitter_id, unit_number, area_id, status_id, order_description)
VALUES (4, 3001, 3, 3, 'AC broken');
INSERT INTO work_orders (submitter_id, unit_number, area_id, status_id, order_description)
VALUES (4, 2001, 2, 2, 'Outlet unpowered');

COMMIT TRANSACTION;

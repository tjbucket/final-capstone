BEGIN TRANSACTION;

DROP TABLE IF EXISTS photos, comments, work_orders, work_statuses, work_areas, persons, units, roles, users CASCADE;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE roles (
    role_id SERIAL,
    role_description varchar(25) NOT NULL,
    CONSTRAINT PK_role PRIMARY KEY (role_id)
);

CREATE TABLE units (
    unit_number int,
    unit_description varchar(20) DEFAULT '',
    property_comment varchar(500) DEFAULT 'No details specified.',
    CONSTRAINT PK_unit PRIMARY KEY (unit_number)
);

CREATE TABLE persons (
    person_id SERIAL,
    user_id int NOT NULL,
    role_id int,
    unit_number int,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    email varchar(100) NOT NULL,
    property_comment varchar(500) DEFAULT 'No details specified.',
    CONSTRAINT PK_person PRIMARY KEY (person_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT FK_role FOREIGN KEY (role_id) REFERENCES roles (role_id),
    CONSTRAINT FK_unit FOREIGN KEY (unit_number) REFERENCES units (unit_number)
);

CREATE TABLE work_areas (
    area_id SERIAL,
    area_description varchar(25),
    CONSTRAINT PK_area PRIMARY KEY (area_id)
);

CREATE TABLE work_statuses (
    status_id SERIAL,
    status_description varchar(25),
    CONSTRAINT PK_status PRIMARY KEY (status_id)
);

CREATE TABLE work_orders (
    order_id SERIAL,
    submitter_id int NOT NULL,
    unit_number int,
    area_id int,
    status_id int,
    order_description varchar(300),
    when_posted timestamp DEFAULT LOCALTIMESTAMP,
    when_accepted timestamp,
    is_emergency boolean DEFAULT FALSE,
    rating int,
    CONSTRAINT PK_order PRIMARY KEY (order_id),
    CONSTRAINT FK_submitter FOREIGN KEY (submitter_id) REFERENCES persons (person_id),
    CONSTRAINT FK_unit FOREIGN KEY (unit_number) REFERENCES units (unit_number),
    CONSTRAINT FK_area FOREIGN KEY (area_id) REFERENCES work_areas (area_id),
    CONSTRAINT FK_status FOREIGN KEY (status_id) REFERENCES work_statuses (status_id)
);

CREATE TABLE comments (
    comment_id SERIAL,
    order_id int,
    poster_id int,
    comment_description varchar(300),
    when_posted timestamp DEFAULT LOCALTIMESTAMP,
    CONSTRAINT PK_comment PRIMARY KEY (comment_id),
    CONSTRAINT FK_order FOREIGN KEY (order_id) REFERENCES work_orders (order_id),
    CONSTRAINT FK_poster FOREIGN KEY (poster_id) REFERENCES persons (person_id)
);

CREATE TABLE photos (
    photo_id SERIAL,
    comment_id int,
    location varchar(150),
    CONSTRAINT PK_photo PRIMARY KEY (photo_id),
    CONSTRAINT FK_comment FOREIGN KEY (comment_id) REFERENCES comments (comment_id)
);

CREATE TABLE work_order_images(
        image_id SERIAL,
        order_id int,
        img_secure_url varchar(200),
        CONSTRAINT PK_image PRIMARY KEY (image_id),
        CONSTRAINT FK_order FOREIGN KEY (order_id) REFERENCES work_orders (order_id)
);

COMMIT TRANSACTION;

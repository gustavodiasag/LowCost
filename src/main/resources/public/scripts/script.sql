-- Script for table user --
CREATE SEQUENCE "public".id_user;

CREATE TABLE "public".user (
	
	id INT PRIMARY KEY NOT NULL DEFAULT nextval(id_user),
	name VARCHAR(128) NOT NULL,
	login VARCHAR(128) NOT NULL,
	email VARCHAR(128) NOT NULL,
	password VARCHAR(128) NOT NULL
);

ALTER SEQUENCE id_user OWNED BY "public".user.id;


-- Script for table company --
CREATE SEQUENCE "public".id_company;

CREATE TABLE "public".company (

	id INT PRIMARY KEY NOT NULL DEFAULT nextval('id_company'),
	name VARCHAR(128) NOT NULL
);

ALTER SEQUENCE id_company OWNED BY "public".company.id;


-- Script for table forum --
CREATE SEQUENCE "public".id_forum;

CREATE TABLE "public".forum (

	id INT PRIMARY KEY NOT NULL DEFAULT nextval('id_forum'),
	title VARCHAR(128) NOT NULL,
	comments INT NOT NULL,
	user_id_fk INT NOT NULL REFERENCES "public".user(id)
);

ALTER SEQUENCE id_forum OWNED BY "public".forum.id;


-- Script for table service --
CREATE SEQUENCE "public".id_service;

CREATE TABLE "public".service (

	id INT PRIMARY KEY NOT NULL DEFAULT nextval('id_service'),
	description VARCHAR(128) NOT NULL,
	price FLOAT NOT NULL,
);

ALTER SEQUENCE id_forum OWNED BY "public".service.id;


-- Script for table offer --
CREATE TABLE "public".offer (

	company_id_fk INT NOT NULL REFERENCES "public".company(id),
	service_id_fk INT NOT NULL REFERENCES "public".service(id)
);


-- Script for table comment --
CREATE TABLE "public".comment (
	
	user_id_fk INT NOT NULL REFERENCES "public".user(id),
	content TEXT,
	submission TIMESTAMP NOT NULL,
	service_id_fk INT REFERENCES "public".service(id),
	forum_id_fk INT REFERENCES "public".company(id)	
);

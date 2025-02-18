drop table if exists users cascade;
drop table if exists tokenvault cascade;

CREATE TABLE IF NOT EXISTS users
(
    id bigserial PRIMARY KEY,
    name text,
    lastname text,
    email text unique,
    password text,
    phone text,
    created timestamptz,
    updated timestamptz,
    image bytea,
    lastlogindate timestamptz,
    userstatus integer,
    username text
);

create table tokenvault
(
	id bigserial primary key,
	token text,
	created timestamptz,
	expired timestamptz,
	username text,
	userid bigint
);


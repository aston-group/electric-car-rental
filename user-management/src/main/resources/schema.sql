drop table if exists users cascade;

CREATE TABLE IF NOT EXISTS users
(
    id bigserial PRIMARY KEY,
    email text unique NOT NULL,
    name text NOT NULL,
    lastname text,
    password text NOT NULL,
    phone text,
    created timestamptz,
    updated timestamptz,
    image bytea,
    lastlogindate timestamptz DEFAULT now(),
    userstatus integer DEFAULT 1,

    /*Это поле требует org.springframework.security */
    /*Для реализации UserDetailsService*/
    username text
);


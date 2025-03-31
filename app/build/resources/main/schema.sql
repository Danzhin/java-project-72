drop table if exists urls;
drop table if exists urlChecks;

create table urls (
    id serial primary key,
    name varchar(255),
    created_at timestamp
);

create table url_checks (
    id serial primary key,
    url_id integer references urls(id),
    status_code integer,
    h1 varchar(255),
    title varchar(255),
    description text,
    created_at timestamp
)
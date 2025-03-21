drop table if exists urls;

create table urls (
    id serial primary key,
    name varchar(255),
    createdAt timestamp
);
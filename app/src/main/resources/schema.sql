drop table if exists urls;

create table urls (
    id bigint primary key auto_increment,
    name varchar(255),
    createdAt timestamp
);
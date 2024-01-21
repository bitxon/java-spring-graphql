create table organization
(
    id   serial primary key,
    name varchar(255) not null
);

create table employee
(
    id              serial primary key,
    email           varchar(255) not null unique,
    name            varchar(255) not null,
    organization_id int          not null,
    foreign key (organization_id) references organization (id)
);
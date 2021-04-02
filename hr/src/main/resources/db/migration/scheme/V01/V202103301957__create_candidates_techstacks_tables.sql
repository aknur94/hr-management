create table candidates(
    candidates_id serial primary key ,
    full_name varchar(128) not null unique,
    city varchar(128) not null ,
    phone varchar(10) unique
);

create table tech_stacks(
    tech_stacks_id serial primary key,
    stack_name varchar(128) unique
);

create table candidates_tech_stacks(
    candidates_id int references candidates(candidates_id),
    tech_stacks_id int references tech_stacks(tech_stacks_id),
    constraint candidates_tech_stacks_pkey primary key (candidates_id, tech_stacks_id)
);
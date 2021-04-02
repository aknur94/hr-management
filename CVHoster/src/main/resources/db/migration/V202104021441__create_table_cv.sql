create table cv(
                           cv_id serial primary key ,
                           candidate_id integer unique,
                           data bytea
);

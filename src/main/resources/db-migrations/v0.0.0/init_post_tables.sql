create table if not exists "post"
(
    id    bigserial unique primary key,
    title varchar
);

create table if not exists "post_uuid"
(
    id    uuid unique primary key,
    title varchar
);
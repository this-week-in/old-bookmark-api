create table if not exists bookmark
(
    bookmark_id         serial                not null
        constraint bookmark_pkey
            primary key,
    extended            varchar(10000),
    description         varchar(10000),
    meta                varchar(10000),
    hash                varchar(1000)         not null
        constraint bookmark_hash_key
            unique,
    href                varchar(1000)         not null,
    time                timestamp             not null,
    tags                varchar(100)[]        not null,
    publish_key         varchar(255),
    edited              timestamp,
    deleted             boolean default false not null,
    ingest_synchronized boolean default false
);


create table if not exists bookmark_years_months
(
    year   bigint       not null,
    month  bigint       not null,
    id     serial       not null
        constraint bookmark_years_months_pkey
            primary key,
    ym_key varchar(255) not null
        constraint bookmark_years_months_ym_key_key
            unique
);


create table if not exists editor_users
(
    id       serial       not null
        constraint editor_users_pkey primary key,
    username varchar(255) not null,
    password varchar(255) not null
);



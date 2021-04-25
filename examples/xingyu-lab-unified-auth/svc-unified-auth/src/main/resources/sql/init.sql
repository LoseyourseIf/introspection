drop table if exists oauth.unified_user;
create table if not exists oauth.unified_user
(
    unified_user_id bigint      not null,
    user_name       varchar(32) not null,
    user_pwd        text        null,
    non_locked      tinyint(1)  null,
    non_expired     tinyint(1)  null,
    create_time     datetime    null,
    create_by       bigint      null,
    update_time     datetime    null,
    update_by       bigint      null,
    enabled         tinyint(1)  null,
    constraint unified_user_id_index
        unique (unified_user_id)
);

alter table oauth.unified_user
    add primary key (unified_user_id);

drop table if exists oauth.unified_app_keys;
create table oauth.unified_app_keys
(
    unified_keypair_id bigint     not null,
    unified_app_id     bigint     null,
    app_pub_key        text       null,
    app_pri_key        text       null,
    create_time        datetime   null,
    create_by          bigint     null,
    update_time        datetime   null,
    update_by          bigint     null,
    enabled            tinyint(1) null,
    constraint unified_app_keys_keypair_id_index
        unique (unified_keypair_id)
);

alter table oauth.unified_app_keys
    add primary key (unified_keypair_id);

drop table if exists oauth.unified_access_token;
create table oauth.unified_access_token
(
    unified_access_token_id bigint      not null,
    unified_app_id          bigint      null,
    access_token            text        null,
    refresh_token           text        null,
    user_name               varchar(50) null,
    grant_type              varchar(20) null,
    not_before              datetime    null,
    expire_at               datetime    null,
    create_time             datetime    null,
    create_by               bigint      null,
    update_time             datetime    null,
    update_by               bigint      null,
    enabled                 tinyint(1)  null,
    constraint unified_access_token_id_index
        unique (unified_access_token_id)
);
alter table oauth.unified_access_token
    add primary key (unified_access_token_id);

drop table if exists oauth.unified_refresh_token;
create table oauth.unified_refresh_token
(
    unified_refresh_token_id bigint      not null,
    unified_app_id           bigint      null,
    user_name                varchar(50) null,
    refresh_token            text        null,
    not_before               datetime    null,
    expire_at                datetime    null,
    create_time              datetime    null,
    create_by                bigint      null,
    update_time              datetime    null,
    update_by                bigint      null,
    enabled                  tinyint(1)  null,
    constraint unified_refresh_token_id_index
        unique (unified_refresh_token_id)
);
alter table oauth.unified_refresh_token
    add primary key (unified_refresh_token_id);

drop table if exists oauth.unified_code;
create table oauth.unified_code
(
    unified_code_id bigint      not null,
    unified_app_id  bigint      null,
    user_name       varchar(50) null,
    unified_code    text        null,
    non_used        tinyint(1)  null,
    non_confirm     tinyint(1)  null,
    not_before      datetime    null,
    expire_at       datetime    null,
    create_time     datetime    null,
    create_by       bigint      null,
    update_time     datetime    null,
    update_by       bigint      null,
    enabled         tinyint(1)  null,
    constraint unified_code_id_index
        unique (unified_code_id)
);
alter table oauth.unified_code
    add primary key (unified_code_id);
-- And example of comment
    create table sample_entity (
        sample_entity_id bigint not null AUTO_INCREMENT,
        name varchar(255) not null,
        entity_code varchar(255) not null,
        primary key (sample_entity_id),
        constraint U_code UNIQUE (entity_code)
    );

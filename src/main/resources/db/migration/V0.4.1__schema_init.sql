create table specifications
(
    id                  bigint primary key generated always as identity,
    specification_title varchar(32)       not null,
    product_type        varchar(32)       not null,
    tested_percentage   integer default 0 not null
);

create table kits
(
    id                    bigint primary key generated always as identity,
    kit_number            varchar(64)       not null,
    batches_quantity      integer default 1 not null,
    batch_size            integer default 1 not null,
    acceptance_percentage integer default 10,
    specification_id      bigint
        constraint fk_kit_specification references specifications on delete cascade
);

create table batches
(
    id           bigint generated always as identity primary key,
    batch_number integer not null,
    kit_id       bigint
        constraint fk_batch_kit references kits on delete cascade
);

create table products
(
    id             bigint generated always as identity primary key,
    product_number integer              not null,
    active         boolean default true not null,
    locked         boolean default false,
    batch_id       bigint
        constraint fk_product_batch references batches on delete cascade
);

create table operation_types
(
    id               bigint generated always as identity primary key,
    type             varchar(64) not null,
    sequence_number  integer     not null,
    specification_id bigint
        constraint fk_specification_action_specification references specifications on delete cascade,
    constraint ux_operation_type unique (specification_id, sequence_number) deferrable initially deferred
);

create table employees
(
    id         uuid        default gen_random_uuid() primary key,
    first_name varchar(32)                    not null,
    last_name  varchar(32)                    not null,
    phone      varchar(16)                    not null,
    password   varchar(32),
    active     boolean     default true,
    role       varchar(32) default 'OPERATOR' not null
);

create table actions
(
    id                bigint generated always as identity primary key,
    done_time         timestamp,
    repair            boolean default false,
    operation_type_id bigint not null
        constraint fk_action_operation_type references operation_types on delete cascade,
    product_id        bigint not null
        constraint fk_action_product references products on delete cascade,
    employee_id       uuid   not null
        constraint fk_action_employee references employees
);

create unique index ux_action_product_operation
    on actions (product_id, operation_type_id)
    where (repair IS FALSE);


create table control_actions
(
    id                bigint generated always as identity primary key,
    done_time         timestamp,
    successful        boolean default true,
    control_type      varchar(64),
    comment           text,
    need_repair       boolean default false,
    operation_type_id bigint not null
        constraint fk_control_action_operation_type references operation_types on delete cascade,
    product_id        bigint not null
        constraint fk_control_action_product references products on delete cascade,
    employee_id       uuid   not null
        constraint fk_control_action_employee references employees
);

create unique index ux_control_action_product_operation
    on control_actions (product_id, operation_type_id, control_type)
    where (successful IS TRUE);

create table acceptance_actions
(
    id          bigint generated always as identity primary key,
    done_time   timestamp,
    product_id  bigint not null unique
        constraint fk_acc_action_product references products on delete cascade,
    employee_id uuid   not null
        constraint fk_acc_action_employee references employees
);

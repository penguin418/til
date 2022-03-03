create table users (
	id integer not null auto_increment,
    email varchar(32),
    password varchar(16),
    name varchar(16),

	constraint pk_users primary key (id),
	constraint uq_users unique (email)
);

create table roles (
    id integer not null auto_increment,
    role varchar(16),

    constraint pk_roles primary key (id),
    constraint uq_roles unique (role)
);

create table users_roles (
    id integer not null auto_increment,
    user_id integer not null,
    role_id integer not null,

    constraint pk_users_roles primary key (id),
    constraint fk_users_roles_users foreign key (user_id) references users(id),
    constraint fk_users_roles_roles foreign key (role_id) references roles(id),
    constraint uq_users_roles unique (user_id, role_id)
);

insert into roles values(null, 'admin');
insert into users values(null, 'test@cj.net', 'test', 'tester');
insert into users_roles values(null,
    (select id from users where name='tester'),
    (select id from roles where role='admin')
);
create table usuario
(
    id bigint auto_increment primary key,
    nome varchar(255) not null,
    senha varchar(255) not null,
    email varchar(255) not null
);

create table cadastro
(
    id bigint auto_increment primary key,
    nome varchar(255) not null,
    email varchar(255) not null,
    telefone varchar(255),
    empresa varchar(255)
);

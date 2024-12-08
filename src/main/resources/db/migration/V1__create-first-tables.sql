CREATE TABLE cursos(
    id bigint not null primary key auto_increment,
    nombre varchar(120) not null,
    categoria varchar(40) not null

);

CREATE table perfiles(
    id bigint not null primary key auto_increment,
    nombre varchar(50) not null
);

CREATE TABLE usuarios(
	id bigint not null primary key auto_increment,
    nombre varchar(200) not null,
    correoElectronico varchar(100) not null,
    contrasena varchar(300) not null
);

CREATE TABLE perfil_usuario(
    id_usuario bigint not null,
    id_perfil bigint not null,

    primary key (id_usuario, id_perfil),
    constraint fk_perfil_usuario_perfil foreign key(id_perfil) references perfiles(id),
    constraint fk_perfil_usuario_usuario foreign key(id_usuario) references usuarios(id)
);

CREATE TABLE topicos (
id bigint not null primary key auto_increment,
titulo varchar(150) not null,
mensaje varchar(500) not null,
fechaCreacion datetime default now(),
status varchar(30) not null,
curso bigint not null,
autor bigint not null,

constraint fk_curso_id foreign key(curso) references cursos(id),
constraint fk_autor_id foreign key(autor) references usuarios(id)
);

CREATE TABLE respuestas(
	id bigint not null primary key auto_increment,
    mensaje varchar(300) not null,
    topico bigint not null,
    fechaCreacion datetime default now(),
    autor bigint not null,
    solucion varchar(200),

    constraint fk_topico_id foreign key(topico) references topicos(id),
    constraint fk_autor_1_id foreign key(autor) references usuarios(id)
);

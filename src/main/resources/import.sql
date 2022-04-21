INSERT INTO profesores (nombre, apellido, email, create_at) VALUES ('Jhoan', 'Carlosama', 'jhoan@gmail.com', '2022-01-01');
INSERT INTO profesores (nombre, apellido, email, create_at) VALUES ('Pepe', 'Perez', 'pepe@gmail.com', '2022-01-01');
INSERT INTO profesores (nombre, apellido, email, create_at) VALUES ('Flor', 'Flores', 'flor@gmail.com', '2022-01-01');
INSERT INTO profesores (nombre, apellido, email, create_at) VALUES ('Juan', 'Josa', 'juan@gmail.com', '2022-01-01');
INSERT INTO profesores (nombre, apellido, email, create_at) VALUES ('Jorge', 'Paz', 'jorge@gmail.com', '2022-01-01');
INSERT INTO profesores (nombre, apellido, email, create_at) VALUES ('Felipe', 'Pelaez', 'felipe@gmail.com', '2022-01-01');
INSERT INTO profesores (nombre, apellido, email, create_at) VALUES ('Flora', 'Marin', 'flora@gmail.com', '2022-01-01');
INSERT INTO profesores (nombre, apellido, email, create_at) VALUES ('Dilan', 'Lopez', 'dilan@gmail.com', '2022-01-01');
INSERT INTO profesores (nombre, apellido, email, create_at) VALUES ('John', 'Lopez', 'john@gmail.com', '2022-01-01');
INSERT INTO profesores (nombre, apellido, email, create_at) VALUES ('Ana', 'Andes', 'ana@gmail.com', '2022-01-01');
INSERT INTO profesores (nombre, apellido, email, create_at) VALUES ('Pilar', 'Pastas', 'pilar@gmail.com', '2022-01-01');
INSERT INTO profesores (nombre, apellido, email, create_at) VALUES ('Pedro', 'Diaz', 'pedro@gmail.com', '2022-01-01');
INSERT INTO profesores (nombre, apellido, email, create_at) VALUES ('Violeta', 'Diaz', 'violeta@gmail.com', '2022-01-01');
INSERT INTO profesores (nombre, apellido, email, create_at) VALUES ('Daniela', 'Pantoja', 'daniela@gmail.com', '2022-01-01');

INSERT INTO cursos (nombre, profesor_id) VALUES ('Artes', 1);

INSERT INTO examenes (nombre, fecha, inicio, fin, curso_id) VALUES ('Examen11', '2022-01-01', '10:00', '12:00', 1);

/* usuarios y roles */
INSERT INTO usuarios (username, password, nombre, apellido, email, enabled) VALUES ('jhoan', '$2a$10$rSg8no54yNofdsAO8jA8OeIyjGPin/KsTKIT1aaqZLF7RPcSywvp2', 'jhoan', 'pantoja', 'jhoan@gmail.com', 1);
INSERT INTO usuarios (username, password, nombre, apellido, email, enabled) VALUES ('admin', '$2a$10$XSB6lBNFnb4l/3MJtrEU2e20Eeh0xYYKjUgEhPpvSgVoTKV/r5Y86', 'admin', 'admin', 'admin@gmail.com', 1);

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO users_authorities (user_id, role_id) VALUES (1, 1);
INSERT INTO users_authorities (user_id, role_id) VALUES (2, 2);
INSERT INTO users_authorities (user_id, role_id) VALUES (2, 1);

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    UNIQUE (nombre)
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    name VARCHAR(255),
    phone VARCHAR(20),
    is_active BOOLEAN DEFAULT TRUE,
    creation_date TIMESTAMP,
    last_access TIMESTAMP
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user
      FOREIGN KEY(user_id)
      REFERENCES users(id)
      ON DELETE CASCADE,
    CONSTRAINT fk_role
      FOREIGN KEY(role_id)
      REFERENCES roles(id)
      ON DELETE CASCADE
);

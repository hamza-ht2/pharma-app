CREATE TABLE users (
                       id                  UUID            NOT NULL,
                       full_name           VARCHAR(150)    NOT NULL,
                       email               VARCHAR(150)    NOT NULL,
                       phone               VARCHAR(20)     NOT NULL,
                       password            VARCHAR(255)    NOT NULL,
                       role                VARCHAR(20)     NOT NULL,
                       avatar_url          VARCHAR(500),
                       active              BOOLEAN         NOT NULL DEFAULT TRUE,
                       last_login_at       TIMESTAMP(6),
                       password_changed_at TIMESTAMP(6),
                       created_at          TIMESTAMP(6)    NOT NULL,
                       updated_at          TIMESTAMP(6)    NOT NULL,

                       CONSTRAINT pk_users PRIMARY KEY (id),
                       CONSTRAINT uk_user_email UNIQUE (email),
                       CONSTRAINT uk_user_phone UNIQUE (phone)
);
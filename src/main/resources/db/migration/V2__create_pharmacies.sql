CREATE TABLE pharmacies (
                            id               UUID            NOT NULL,
                            name             VARCHAR(150)    NOT NULL,
                            license_number   VARCHAR(100)    NOT NULL,
                            address          VARCHAR(255)    NOT NULL,
                            city             VARCHAR(100)    NOT NULL,
                            phone            VARCHAR(20)     NOT NULL,
                            email            VARCHAR(150)    NOT NULL,
                            owner_user_id    UUID            NOT NULL,
                            active           BOOLEAN         NOT NULL DEFAULT TRUE,
                            created_at       TIMESTAMP(6)    NOT NULL,
                            updated_at       TIMESTAMP(6)    NOT NULL,

                            CONSTRAINT pk_pharmacies PRIMARY KEY (id),
                            CONSTRAINT uk_pharmacy_license_number UNIQUE (license_number),
                            CONSTRAINT uk_pharmacy_phone UNIQUE (phone),
                            CONSTRAINT uk_pharmacy_email UNIQUE (email),
                            CONSTRAINT fk_pharmacies_owner FOREIGN KEY (owner_user_id) REFERENCES users (id)
);
CREATE TABLE delivery_confirmations (
                                        id                  UUID            NOT NULL,
                                        delivery_id         UUID            NOT NULL,
                                        token               VARCHAR(255)    NOT NULL,
                                        expires_at          TIMESTAMP(6)    NOT NULL,
                                        used_at             TIMESTAMP(6),
                                        scanned_by_user_id  UUID,
                                        scanned_at          TIMESTAMP(6),
                                        latitude            DOUBLE PRECISION,
                                        longitude           DOUBLE PRECISION,
                                        photo_url           VARCHAR(500),
                                        signature_url       VARCHAR(500),
                                        notes               TEXT,
                                        partial             BOOLEAN         NOT NULL DEFAULT FALSE,
                                        created_at          TIMESTAMP(6)    NOT NULL,

                                        CONSTRAINT pk_delivery_confirmations PRIMARY KEY (id),
                                        CONSTRAINT uk_delivery_confirmation_token UNIQUE (token),
                                        CONSTRAINT uk_delivery_confirmation_delivery UNIQUE (delivery_id),
                                        CONSTRAINT fk_delivery_confirmations_delivery FOREIGN KEY (delivery_id) REFERENCES deliveries (id),
                                        CONSTRAINT fk_delivery_confirmations_user FOREIGN KEY (scanned_by_user_id) REFERENCES users (id)
);
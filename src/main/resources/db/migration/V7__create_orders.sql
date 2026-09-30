CREATE TABLE orders (
                        id                  UUID            NOT NULL,
                        pharmacy_id         UUID            NOT NULL,
                        placed_by_user_id   UUID            NOT NULL,
                        status              VARCHAR(30)     NOT NULL,
                        subtotal            NUMERIC(12,2)   NOT NULL,
                        total               NUMERIC(12,2)   NOT NULL,
                        notes               TEXT,
                        rejection_reason    VARCHAR(500),
                        created_at          TIMESTAMP(6)    NOT NULL,
                        confirmed_at        TIMESTAMP(6),
                        cancelled_at        TIMESTAMP(6),

                        CONSTRAINT pk_orders PRIMARY KEY (id),
                        CONSTRAINT fk_orders_pharmacy FOREIGN KEY (pharmacy_id) REFERENCES pharmacies (id),
                        CONSTRAINT fk_orders_placed_by FOREIGN KEY (placed_by_user_id) REFERENCES users (id)
);
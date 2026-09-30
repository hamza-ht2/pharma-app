CREATE TABLE deliveries (
                            id             UUID            NOT NULL,
                            order_id       UUID            NOT NULL,
                            driver_id      UUID            NOT NULL,
                            status         VARCHAR(30)     NOT NULL,
                            assigned_at    TIMESTAMP(6)    NOT NULL,
                            picked_up_at   TIMESTAMP(6),
                            delivered_at   TIMESTAMP(6),
                            failed_reason  VARCHAR(500),
                            created_at     TIMESTAMP(6)    NOT NULL,

                            CONSTRAINT pk_deliveries PRIMARY KEY (id),
                            CONSTRAINT uk_delivery_order UNIQUE (order_id),
                            CONSTRAINT fk_deliveries_order FOREIGN KEY (order_id) REFERENCES orders (id),
                            CONSTRAINT fk_deliveries_driver FOREIGN KEY (driver_id) REFERENCES users (id)
);
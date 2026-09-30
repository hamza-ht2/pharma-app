CREATE TABLE product_batches (
                                 id                   UUID            NOT NULL,
                                 product_id           UUID            NOT NULL,
                                 batch_number         VARCHAR(100)    NOT NULL,
                                 expiry_date          DATE            NOT NULL,
                                 quantity_on_hand     INTEGER         NOT NULL DEFAULT 0,
                                 quantity_reserved    INTEGER         NOT NULL DEFAULT 0,
                                 low_stock_threshold  INTEGER         NOT NULL DEFAULT 10,
                                 active               BOOLEAN         NOT NULL DEFAULT TRUE,
                                 created_at           TIMESTAMP(6)    NOT NULL,
                                 updated_at           TIMESTAMP(6)    NOT NULL,

                                 CONSTRAINT pk_product_batches PRIMARY KEY (id),
                                 CONSTRAINT uk_product_batch UNIQUE (product_id, batch_number),
                                 CONSTRAINT fk_product_batches_product FOREIGN KEY (product_id) REFERENCES products (id)
);
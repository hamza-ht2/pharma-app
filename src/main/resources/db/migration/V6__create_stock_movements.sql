CREATE TABLE stock_movements (
                                 id                  UUID            NOT NULL,
                                 product_id          UUID            NOT NULL,
                                 product_batch_id    UUID,
                                 change_number       INTEGER         NOT NULL,
                                 reason              VARCHAR(30)     NOT NULL,
                                 reference_id        UUID,
                                 note                VARCHAR(500),
                                 created_by_user_id  UUID            NOT NULL,
                                 created_at          TIMESTAMP(6)    NOT NULL,

                                 CONSTRAINT pk_stock_movements PRIMARY KEY (id),
                                 CONSTRAINT fk_stock_movements_product FOREIGN KEY (product_id) REFERENCES products (id),
                                 CONSTRAINT fk_stock_movements_batch FOREIGN KEY (product_batch_id) REFERENCES product_batches (id),
                                 CONSTRAINT fk_stock_movements_user FOREIGN KEY (created_by_user_id) REFERENCES users (id)
);
CREATE TABLE delivery_items (
                                id                  UUID            NOT NULL,
                                delivery_id         UUID            NOT NULL,
                                order_item_id       UUID            NOT NULL,
                                product_id          UUID            NOT NULL,
                                product_batch_id    UUID            NOT NULL,
                                quantity_delivered  INTEGER         NOT NULL,
                                quantity_missing    INTEGER         NOT NULL,
                                note                VARCHAR(500),

                                CONSTRAINT pk_delivery_items PRIMARY KEY (id),
                                CONSTRAINT fk_delivery_items_delivery FOREIGN KEY (delivery_id) REFERENCES deliveries (id),
                                CONSTRAINT fk_delivery_items_order_item FOREIGN KEY (order_item_id) REFERENCES order_items (id),
                                CONSTRAINT fk_delivery_items_product FOREIGN KEY (product_id) REFERENCES products (id),
                                CONSTRAINT fk_delivery_items_batch FOREIGN KEY (product_batch_id) REFERENCES product_batches (id)
);
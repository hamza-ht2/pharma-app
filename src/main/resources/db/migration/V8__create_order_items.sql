CREATE TABLE order_items (
                             id           UUID            NOT NULL,
                             order_id     UUID            NOT NULL,
                             product_id   UUID            NOT NULL,
                             quantity     INTEGER         NOT NULL,
                             unit_price   NUMERIC(12,2)   NOT NULL,
                             line_total   NUMERIC(12,2)   NOT NULL,

                             CONSTRAINT pk_order_items PRIMARY KEY (id),
                             CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders (id),
                             CONSTRAINT fk_order_items_product FOREIGN KEY (product_id) REFERENCES products (id)
);
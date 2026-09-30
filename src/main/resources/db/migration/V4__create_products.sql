CREATE TABLE products (
                          id                    UUID            NOT NULL,
                          name                  VARCHAR(200)    NOT NULL,
                          description           TEXT,
                          category_id           UUID            NOT NULL,
                          manufacturer          VARCHAR(150),
                          dosage                VARCHAR(100),
                          form                  VARCHAR(100),
                          barcode               VARCHAR(100),
                          unit_price            NUMERIC(12,2)   NOT NULL,
                          prescription_required BOOLEAN         NOT NULL DEFAULT FALSE,
                          active                BOOLEAN         NOT NULL DEFAULT TRUE,
                          image_url             VARCHAR(500),
                          created_at            TIMESTAMP(6)    NOT NULL,
                          updated_at            TIMESTAMP(6)    NOT NULL,

                          CONSTRAINT pk_products PRIMARY KEY (id),
                          CONSTRAINT uk_product_barcode UNIQUE (barcode),
                          CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories (id)
);
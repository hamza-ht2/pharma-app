CREATE TABLE categories (
                            id                  UUID            NOT NULL,
                            name                VARCHAR(150)    NOT NULL,
                            description         TEXT,
                            name_fr             VARCHAR(150)    NOT NULL,
                            name_ar             VARCHAR(150)    NOT NULL,
                            parent_category_id  UUID,
                            display_order       INTEGER         NOT NULL DEFAULT 0,
                            active              BOOLEAN         NOT NULL DEFAULT TRUE,
                            created_at          TIMESTAMP(6)    NOT NULL,
                            updated_at          TIMESTAMP(6)    NOT NULL,

                            CONSTRAINT pk_categories PRIMARY KEY (id),
                            CONSTRAINT uk_category_name UNIQUE (name),
                            CONSTRAINT fk_categories_parent FOREIGN KEY (parent_category_id) REFERENCES categories (id)
);
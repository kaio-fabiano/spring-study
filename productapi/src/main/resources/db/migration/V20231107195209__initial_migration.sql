create table if not exists product
(
    id          UUID NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    name        VARCHAR(255),
    description VARCHAR(255),
    price       DOUBLE PRECISION,
    quantity    INTEGER,
    CONSTRAINT pk_product PRIMARY KEY (id)
);
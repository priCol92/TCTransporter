CREATE TABLE offices
(
    id                 BIGSERIAL PRIMARY KEY,
    name               TEXT        NOT NULL,
    city               TEXT        NOT NULL,
    address            TEXT        NOT NULL,
    undergrounds       TEXT[]      NOT NULL DEFAULT '{}',
    working_hours      TEXT[]      NOT NULL DEFAULT '{"Пн-Вс 09:00-20:00"}',
    restriction_weight TEXT        NOT NULL,
    description        TEXT        NOT NULL,
    payment_methods    TEXT[]      NOT NULL DEFAULT '{"Наличные","Банковские карты", "Наложенный платеж"}',
    requisite_phone    TEXT        NOT NULL DEFAULT '+7(843)XXX-XX-XX',
    requisite_email    TEXT        NOT NULL DEFAULT 'transporter@example.ru',
    removed            BOOLEAN     NOT NULL DEFAULT FALSE,
    current_time_now   timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE routes
(
    id        BIGSERIAL PRIMARY KEY,
    from_city TEXT NOT NULL,
    to_city   TEXT NOT NULL
);

CREATE TABLE route_parts
(
    id          BIGSERIAL PRIMARY KEY,
    route_id    BIGSERIAL REFERENCES routes,
    section     INT CHECK ( section >= 0 )                               DEFAULT 0,
    rate_factor REAL CHECK ( 0.58 < rate_factor AND rate_factor < 1.21 ) DEFAULT 1.0
);

CREATE TABLE tariffs
(
    id          SERIAL PRIMARY KEY,
    name        TEXT        NOT NULL,
    description TEXT        NOT NULL,
    amount_km   REAL        NOT NULL CHECK ( amount_km > 0 ),
    removed     BOOLEAN     NOT NULL DEFAULT FALSE,
    created     timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders
(
    id                  BIGSERIAL PRIMARY KEY,
    tariff_id           SERIAL REFERENCES tariffs,
    tariff_name         TEXT        NOT NULL,
    route_id            BIGSERIAL REFERENCES routes,
    from_city           TEXT        NOT NULL,
    id_office_from_city INTEGER     NOT NULL,
    to_city             TEXT        NOT NULL,
    id_office_to_city   INTEGER     NOT NULL,
    time                TEXT        NOT NULL,
    price               INTEGER     NOT NULL,
    images              TEXT[]      NOT NULL DEFAULT '{"noImage"}',
    created             timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER SEQUENCE orders_id_seq RESTART WITH 1000001;

--Most popular office from city
CREATE VIEW popular_office_from_city AS
SELECT ord.from_city, ord.id_office_from_city, count(ord.id_office_from_city) number_of_orders FROM orders ord
GROUP BY ord.id_office_from_city, ord.from_city;

--Most popular office to city
CREATE VIEW popular_office_to_city AS
SELECT ord.to_city, ord.id_office_to_city, count(ord.id_office_to_city) number_of_orders FROM orders ord
GROUP BY ord.id_office_to_city, ord.to_city;

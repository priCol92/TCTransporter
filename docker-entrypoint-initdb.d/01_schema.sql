-- status - работает или нет в данный момент, как привязать
-- types - тип: постомат выдачи или полноценный пункт. Как сделать?
-- restrictions - ограничения по весу,габаритов посылки
-- description - описание как добраться, остановки и т.д
-- проблема с названием contacts, назвал реквизиты
CREATE TABLE offices
(
    id                 BIGSERIAL PRIMARY KEY,
    name               TEXT        NOT NULL,
    city               TEXT        NOT NULL,
    address            TEXT        NOT NULL,
    undergrounds       TEXT[]      NOT NULL DEFAULT '{}',
    working_hours      TEXT[]      NOT NULL DEFAULT '{"Пн-Вс 09:00-20:00"}',
    --status           TEXT        NOT NULL CHECK (  ),
    --types              BOOLEAN     NOT NULL DEFAULT FALSE,
    restriction_weight TEXT        NOT NULL,
    --restriction_package     TEXT     NOT NULL DEFAULT 'Макс размеры для постамата: 65x37x41',
    description        TEXT        NOT NULL,
    payment_methods    TEXT[]      NOT NULL DEFAULT '{"Наличные","Банковские карты", "Наложенный платеж"}',
    --  images           TEXT[2]     NOT NULL,
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
    id          BIGSERIAL PRIMARY KEY,
    --  number    SERIAL,
    tariff_id   SERIAL REFERENCES tariffs,
    tariff_name TEXT        NOT NULL,
    route_id    BIGSERIAL REFERENCES routes,
    from_city   TEXT        NOT NULL,
    to_city     TEXT        NOT NULL,
    time        TEXT        NOT NULL,
    price       INTEGER     NOT NULL,
    images      TEXT[]      NOT NULL DEFAULT '{"noImage"}',
    created     timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER SEQUENCE orders_id_seq RESTART WITH 1000001;


-- CREATE TABLE route_map
-- (
--     id              BIGSERIAL PRIMARY KEY,
--     reception_point TEXT NOT NULL,
--     pickup_point    TEXT NOT NULL,
--     plot1           INT CHECK ( plot1 >= 0 )                                   DEFAULT 0,
--     rate_factor1    REAL CHECK ( 0.58 < rate_factor1 AND rate_factor1 < 1.21 ) DEFAULT 1.0, --коэфициент скорости
--     plot2           INT CHECK ( plot1 >= 0 )                                   DEFAULT 0,
--     rate_factor2    REAL CHECK ( 0.58 < rate_factor2 AND rate_factor2 < 1.21 ) DEFAULT 1.0,
--     plot3           INT CHECK ( plot1 >= 0 )                                   DEFAULT 0,
--     rate_factor3    REAL CHECK ( 0.58 < rate_factor3 AND rate_factor3 < 1.21 ) DEFAULT 1.0,
--     plot4           INT CHECK ( plot1 >= 0 )                                   DEFAULT 0,
--     rate_factor4    REAL CHECK ( 0.58 < rate_factor4 AND rate_factor4 < 1.21 ) DEFAULT 1.0,
--     plot5           INT CHECK ( plot1 >= 0 )                                   DEFAULT 0,
--     rate_factor5    REAL CHECK ( 0.58 < rate_factor5 AND rate_factor5 < 1.21 ) DEFAULT 1.0,
--     UNIQUE (reception_point, pickup_point)                                                  --уникальность двух столбцов
-- );
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
)
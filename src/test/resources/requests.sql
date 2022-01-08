-- The number of orders and the value of all orders
SELECT sum(price), count(*)
FROM orders;

-- Most popular route
SELECT from_city, to_city, count(route_id)
FROM orders
GROUP BY from_city, to_city
ORDER BY -count(route_id);

-- Most popular tariff
SELECT tariff_id, tariff_name, count(tariff_id)
FROM orders
GROUP BY tariff_id, tariff_name
ORDER BY -count(tariff_id);

--Route sections
SELECT r.id, r.from_city, rp.section, r.to_city
FROM routes r
         JOIN route_parts rp ON r.id = rp.route_id;

--Route distance
SELECT r.id, r.from_city, r.to_city, sum(rp.section) distance
FROM routes r
         JOIN route_parts rp ON r.id = rp.route_id
GROUP BY r.id, r.from_city, r.to_city
ORDER BY r.id;

--Most popular office to city
SELECT potc.to_city, of.name, potc.id_office_to_city, potc.number_of_orders
FROM popular_office_to_city potc
         JOIN offices of ON potc.id_office_to_city = of.id
WHERE to_city = 'Kazan'
ORDER BY -number_of_orders;

--Most popular office from city
SELECT pofc.from_city, of.name, pofc.id_office_from_city, pofc.number_of_orders
FROM popular_office_from_city pofc
         JOIN offices of ON pofc.id_office_from_city = of.id
WHERE from_city = 'Kazan'
ORDER BY -number_of_orders;



-- No watch

--Most popular office to city
SELECT of.id, of.name, count(ord.id_office_to_city) number_of_orders
FROM offices of
         JOIN orders ord ON of.id = ord.id_office_to_city
GROUP BY of.id
ORDER BY number_of_orders;

SELECT of.id, ord.from_city, ord.id_office_from_city, count(ord.id_office_from_city) number_of_orders
FROM offices of
         JOIN orders ord ON of.id = ord.id_office_from_city
group by ord.id_office_from_city, ord.from_city, of.id;

SELECT ord.to_city, ord.id_office_to_city, count(ord.id_office_to_city) number_of_orders
FROM orders ord
group by ord.id_office_to_city, ord.to_city;


--Most popular office
SELECT (SELECT of.name FROM offices of WHERE of.id = pofc.id_office_from_city AND of.id !=) name_office,
       pofc.id_office_from_city                                                             id_office,
       sum(pofc.number_of_orders_from + potc.number_of_orders_to)                           number_of_orders
FROM popular_office_from_city pofc
         JOIN popular_office_to_city potc ON pofc.id_office_from_city = potc.id_office_to_city

GROUP BY pofc.id_office_from_city
ORDER BY name_office;


SELECT ord.to_city, ord.id_office_to_city, count(ord.id_office_to_city) number_of_orders_to
FROM orders ord
WHERE to_city = 'Moscow'
GROUP BY ord.id_office_to_city, ord.to_city;

--Most popular office 1
SELECT ord.id_office_from_city,
       ord.id_office_to_city,
       count(ord.id_office_from_city) sumFC,
       count(ord.id_office_to_city)   sumTC,
       of.name
FROM orders ord
         JOIN offices of ON ord.id_office_from_city = of.id
GROUP BY ord.id_office_from_city, ord.id_office_to_city, of.name;

--Most popular office from city
SELECT of.id, of.name, count(ord.id_office_from_city) number_of_orders
FROM offices of
         JOIN orders ord ON of.id = ord.id_office_from_city
GROUP BY of.id
ORDER BY number_of_orders;
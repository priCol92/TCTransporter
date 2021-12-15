-- The number of orders and the value of all orders
SELECT sum(price), count(*) FROM orders;

-- Most popular route
SELECT from_city,to_city, count(route_id) FROM orders
GROUP BY from_city, to_city
ORDER BY -count(route_id);

-- Most popular tariff
SELECT tariff_id, tariff_name, count(tariff_id) FROM orders
GROUP BY tariff_id, tariff_name
ORDER BY -count(tariff_id);

--Route sections
SELECT r.id, r.from_city, rp.section, r.to_city FROM routes r
JOIN route_parts rp ON r.id = rp.route_id;

--Route distance
SELECT r.id, r.from_city, r.to_city, sum(rp.section) distance  FROM routes r
JOIN route_parts rp ON r.id = rp.route_id
GROUP BY r.id, r.from_city, r.to_city
ORDER BY r.id;
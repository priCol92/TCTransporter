package org.example.manager;

import lombok.RequiredArgsConstructor;
import org.example.dto.OrderRegisterRequestDTO;
import org.example.dto.OrderRegisterResponseDTO;
import org.example.exception.ProductNotFoundException;
import org.example.model.OrderModel;
import org.example.model.OrderRouteModel;
import org.example.model.OrderRoutePartsModel;
import org.example.model.OrderTariffModel;
import org.example.rowmapper.OrderRoutePartsRowMapper;
import org.example.rowmapper.OrderRouteRowMapper;
import org.example.rowmapper.OrderRowMapper;
import org.example.rowmapper.OrderTariffRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderManager {
    private final NamedParameterJdbcTemplate template;
    private final OrderTariffRowMapper orderTariffRowMapper;
    private final OrderRouteRowMapper orderRouteRowMapper;
    private final OrderRowMapper orderRowMapper;
    private final OrderRoutePartsRowMapper orderRoutePartsRowMapper;
    private final String[] defaultImages = new String[]{"noImage.png"};
    private final int averageSpeed = 70;
    //private final int internalWorksTime = 5;
    private final int travelTime = 9;


    public OrderRegisterResponseDTO register(OrderRegisterRequestDTO requestDTO) {
        try {
            final OrderTariffModel tariff = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id , name, amount_km  FROM tariffs
                            WHERE removed=FALSE AND id = :id
                            """,
                    Map.of("id", requestDTO.getTariffId()),
                    orderTariffRowMapper
            );

            final OrderRouteModel route = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id , from_city, to_city  FROM routes
                            WHERE id = :id
                            """,
                    Map.of("id", requestDTO.getRouteId()),
                    orderRouteRowMapper
            );

            final List<OrderRoutePartsModel> routeParts = template.query(
                    // language=PostgreSQL
                    """
                            SELECT id, route_id, section, rate_factor FROM route_parts
                            WHERE route_id = :id
                            """,
                    Map.of("id", requestDTO.getRouteId()),
                    orderRoutePartsRowMapper
            );

            final OrderModel order = template.queryForObject(
                    // language=PostgreSQL
                    """
                            INSERT INTO orders (tariff_id, tariff_name, route_id, from_city, to_city, time, price, images) 
                            VALUES (:tariffId, :tariffName, :routeId, :fromCity, :toCity, :time, :price, :images)
                            RETURNING id, tariff_id, tariff_name, route_id, from_city, to_city, time, price, images
                            """,
                    Map.of(
                            "tariffId", requestDTO.getTariffId(),
                            "tariffName", tariff.getName(),
                            "routeId", requestDTO.getRouteId(),
                            "fromCity", route.getFromCity(),
                            "toCity", route.getToCity(),
                            "time", calculateTime(routeParts),
                            "price", calculatePrice(routeParts, tariff.getAmountKm()),
                            "images", getImage(requestDTO.getImages())
                    ),
                    orderRowMapper
            );
            return new OrderRegisterResponseDTO(new OrderRegisterResponseDTO.Order(
                    order.getId(),
                    order.getTariffName(),
                    order.getFromCity(),
                    order.getToCity(),
                    order.getTime(),
                    order.getImages(),
                    order.getPrice()
            ));
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException(e);
        }
    }

    private String calculateTime(List<OrderRoutePartsModel> routeParts) {
        String time = null;
        double sumTime = 0.0;
        for (OrderRoutePartsModel routePart : routeParts) {
            final double timeSection = Math.ceil(routePart.getSection() / (routePart.getRateFactor() * averageSpeed));
            sumTime += timeSection;
        }
        //final double ceil = Math.ceil(sumTime / travelTime);
        final int days = (int) Math.ceil(sumTime / travelTime);
        switch (days) {
            case 1:
                time = "Приблизительное время доставки " + days + " день.";
                break;
            case 2, 3, 4:
                time = "Приблизительное время доставки " + days + " дня.";
                break;
            default:
                time = "Приблизительное время доставки " + days + " дней.";
        }

        return time;
    }


    private int calculatePrice(List<OrderRoutePartsModel> routeParts, double amountKm) {
        int totalSum = 0;
        for (OrderRoutePartsModel routePart : routeParts) {
            final double costSection = Math.ceil(routePart.getSection() * amountKm);
            totalSum += costSection;
        }
        return totalSum;
    }

    private String[] getImage(String[] images) {
        if (images == null) {
            return defaultImages;
        }

        if (images.length == 0) {
            return defaultImages;
        }

        return images;
    }
}

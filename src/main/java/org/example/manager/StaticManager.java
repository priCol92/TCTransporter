package org.example.manager;

import lombok.RequiredArgsConstructor;
import org.example.dto.StaticPDOResponseDTO;
import org.example.dto.StaticPPOResponseDTO;
import org.example.dto.StaticRDResponseDTO;
import org.example.model.RouteDistanceModel;
import org.example.model.StaticFromOfficeModel;
import org.example.model.StaticToOfficeModel;
import org.example.rowmapper.RouteDistanceRowMapper;
import org.example.rowmapper.StaticFromOfficeRowMapper;
import org.example.rowmapper.StaticToOfficeRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StaticManager {
    private final NamedParameterJdbcTemplate template;
    private final RouteDistanceRowMapper routeDistanceRowMapper;
    private final StaticFromOfficeRowMapper staticFromOfficeRowMapper;
    private final StaticToOfficeRowMapper staticToOfficeRowMapper;

    public StaticRDResponseDTO getAllRoute() {
        final List<RouteDistanceModel> items = template.query(
                // language=PostgreSQL
                """
                        SELECT r.id, r.from_city, r.to_city, sum(rp.section) distance  FROM routes r
                        JOIN route_parts rp ON r.id = rp.route_id
                        GROUP BY r.id, r.from_city, r.to_city
                        ORDER BY r.id
                        LIMIT 100;
                        """,
                routeDistanceRowMapper
        );
        final StaticRDResponseDTO responseDTO = new StaticRDResponseDTO(new ArrayList<>(items.size()));
        for (RouteDistanceModel item : items) {
            responseDTO.getRouteDistances().add(new StaticRDResponseDTO.RouteDistance(
                    item.getId(),
                    item.getFromCity(),
                    item.getToCity(),
                    item.getDistance()
            ));
        }
        return responseDTO;
    }

    public StaticPDOResponseDTO popularDepartureOffice(String city) {
        final List<StaticFromOfficeModel> items = template.query(
                // language=PostgreSQL
                """
                        SELECT pofc.from_city, of.name, pofc.id_office_from_city, pofc.number_of_orders
                        FROM popular_office_from_city pofc
                                 JOIN offices of ON pofc.id_office_from_city = of.id
                        WHERE from_city = :city
                        ORDER BY -number_of_orders
                        """,
                Map.of("city", city),
                staticFromOfficeRowMapper
        );
        final StaticPDOResponseDTO responseDTO = new StaticPDOResponseDTO(new ArrayList<>(items.size()));
        for (StaticFromOfficeModel item : items) {
            responseDTO.getOffices().add(new StaticPDOResponseDTO.Office(
                    item.getFromCity(),
                    item.getName(),
                    item.getId(),
                    item.getNumberOfOrders()
            ));
        }
        return responseDTO;
    }

    public StaticPPOResponseDTO popularPickupOffice(String city) {
        final List<StaticToOfficeModel> items = template.query(
                // language=PostgreSQL
                """
                        SELECT potc.to_city, of.name, potc.id_office_to_city, potc.number_of_orders
                        FROM popular_office_to_city potc
                                 JOIN offices of ON potc.id_office_to_city = of.id
                        WHERE to_city = :city
                        ORDER BY -number_of_orders
                        """,
                Map.of("city", city),
                staticToOfficeRowMapper
        );
        final StaticPPOResponseDTO responseDTO = new StaticPPOResponseDTO(new ArrayList<>(items.size()));
        for (StaticToOfficeModel item : items) {
            responseDTO.getOffices().add(new StaticPPOResponseDTO.Office(
                    item.getToCity(),
                    item.getName(),
                    item.getId(),
                    item.getNumberOfOrders()
            ));
        }
        return responseDTO;
    }
}

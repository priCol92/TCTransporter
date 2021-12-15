package org.example.rowmapper;

import org.example.model.OrderRoutePartsModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderRoutePartsRowMapper implements RowMapper<OrderRoutePartsModel> {
    @Override
    public OrderRoutePartsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrderRoutePartsModel(
                rs.getLong("id"),
                rs.getLong("route_id"),
                rs.getInt("section"),
                rs.getDouble("rate_factor")
        );
    }
}

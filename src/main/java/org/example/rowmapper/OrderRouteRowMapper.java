package org.example.rowmapper;

import org.example.model.OrderRouteModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderRouteRowMapper implements RowMapper<OrderRouteModel> {
    @Override
    public OrderRouteModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrderRouteModel(
                rs.getLong("id"),
                rs.getString("from_city"),
                rs.getString("to_city")
        );
    }
}

package org.example.rowmapper;

import org.example.model.OrderModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderRowMapper implements RowMapper<OrderModel> {
    @Override
    public OrderModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrderModel(
                rs.getLong("id"),
                rs.getLong("tariff_id"),
                rs.getString("tariff_name"),
                rs.getLong("route_id"),
                rs.getString("from_city"),
                rs.getString("to_city"),
                rs.getString("time"),
                rs.getInt("price"),
                ((String[]) rs.getArray("images").getArray())
        );
    }
}

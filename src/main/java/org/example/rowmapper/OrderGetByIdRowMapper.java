package org.example.rowmapper;

import org.example.model.OrderGetByIdModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderGetByIdRowMapper implements RowMapper<OrderGetByIdModel> {
    @Override
    public OrderGetByIdModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrderGetByIdModel(
                rs.getLong("id"),
                rs.getString("tariff_name"),
                rs.getString("from_city"),
                rs.getString("to_city"),
                rs.getString("time"),
                rs.getInt("price"),
                ((String[]) rs.getArray("images").getArray())
        );
    }
}

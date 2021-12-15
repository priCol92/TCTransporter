package org.example.rowmapper;

import org.example.model.OrderTariffModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderTariffRowMapper implements RowMapper<OrderTariffModel> {
    @Override
    public OrderTariffModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrderTariffModel(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDouble("amount_km")
        );
    }
}

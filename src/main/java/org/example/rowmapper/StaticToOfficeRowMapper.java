package org.example.rowmapper;

import org.example.model.StaticToOfficeModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StaticToOfficeRowMapper implements RowMapper<StaticToOfficeModel> {
    @Override
    public StaticToOfficeModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new StaticToOfficeModel(
                rs.getString("to_city"),
                rs.getString("name"),
                rs.getLong("id_office_to_city"),
                rs.getLong("number_of_orders")
        );
    }
}

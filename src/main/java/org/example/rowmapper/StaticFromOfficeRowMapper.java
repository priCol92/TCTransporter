package org.example.rowmapper;

import org.example.model.StaticFromOfficeModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StaticFromOfficeRowMapper implements RowMapper<StaticFromOfficeModel> {
    @Override
    public StaticFromOfficeModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new StaticFromOfficeModel(
                rs.getString("from_city"),
                rs.getString("name"),
                rs.getLong("id_office_from_city"),
                rs.getLong("number_of_orders")
        );
    }
}

package org.example.rowmapper;

import org.example.model.OfficeFullModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@Component
public class OfficeFullRowMapper implements RowMapper<OfficeFullModel> {
    @Override
    public OfficeFullModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OfficeFullModel(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("city"),
                rs.getString("address"),
                Arrays.asList(
                        (String[]) rs.getArray("undergrounds").getArray()
                ),
                Arrays.asList(
                        ((String[]) rs.getArray("working_hours").getArray())
                ),
                rs.getString("restriction_weight"),
                rs.getString("description"),
                Arrays.asList(
                        ((String[]) rs.getArray("payment_methods").getArray())
                ),
                rs.getString("requisite_phone"),
                rs.getString("requisite_email")
        );
    }
}
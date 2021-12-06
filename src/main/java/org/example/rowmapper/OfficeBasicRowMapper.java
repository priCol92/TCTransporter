package org.example.rowmapper;

import org.example.model.OfficeBasicModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@Component
public class OfficeBasicRowMapper implements RowMapper<OfficeBasicModel> {
    @Override
    public OfficeBasicModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OfficeBasicModel(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("address"),
                Arrays.asList(
                        (String[]) rs.getArray("undergrounds").getArray()
                ),
                Arrays.asList(
                        ((String[]) rs.getArray("working_hours").getArray())
                ),
                rs.getString("restriction_weight")
        );
    }
}

package org.example.rowmapper;

import org.example.model.TariffModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TariffRowMapper implements RowMapper<TariffModel> {
    @Override
    public TariffModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TariffModel(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description")
        );
    }
}

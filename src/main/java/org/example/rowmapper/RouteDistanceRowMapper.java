package org.example.rowmapper;

import org.example.model.RouteDistanceModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RouteDistanceRowMapper implements RowMapper<RouteDistanceModel> {
    @Override
    public RouteDistanceModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RouteDistanceModel(
                rs.getLong("id"),
                rs.getString("from_city"),
                rs.getString("to_city"),
                rs.getInt("distance")
        );
    }
}

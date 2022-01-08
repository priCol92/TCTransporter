package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StaticRDResponseDTO {
    private List<RouteDistance> routeDistances;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class RouteDistance {
        private long id;
        private String fromCity;
        private String toCity;
        private int distance;
    }
}

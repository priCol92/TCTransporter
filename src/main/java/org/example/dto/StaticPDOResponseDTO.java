package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StaticPDOResponseDTO {
    private List<Office> offices;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Office {
        private String fromCity;
        private String name;
        private long id;
        private long numberOfOrders;
    }
}

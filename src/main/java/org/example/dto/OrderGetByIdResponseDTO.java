package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderGetByIdResponseDTO {
    private Order order;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Order {
        private long id;
        private String tariffName;
        private String fromCity;
        private String toCity;
        private String time;
        private int price;
        private String[] images;
    }
}

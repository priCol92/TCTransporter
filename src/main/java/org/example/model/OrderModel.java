package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderModel {
    private long id;
    private long tariffId;
    private String tariffName;
    private long routeId;
    private String fromCity;
    private long idOfficeFromCity;
    private String toCity;
    private long idOfficeToCity;
    private String time;
    private int price;
    private String[] images;
}

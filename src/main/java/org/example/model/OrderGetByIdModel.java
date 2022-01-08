package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderGetByIdModel {
    private long id;
    private String tariffName;
    private String fromCity;
    private String toCity;
    private String time;
    private int price;
    private String[] images;
}

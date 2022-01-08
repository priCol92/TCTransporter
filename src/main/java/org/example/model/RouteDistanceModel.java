package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RouteDistanceModel {
    private long id;
    private String fromCity;
    private String toCity;
    private int distance;
}

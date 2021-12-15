package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRoutePartsModel {
    private long id;
    private long routeId;
    private int section;
    private double rateFactor;
}

package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StaticToOfficeModel {
    private String toCity;
    private String name;
    private long id;
    private long numberOfOrders;
}

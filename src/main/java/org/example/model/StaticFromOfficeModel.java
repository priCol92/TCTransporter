package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StaticFromOfficeModel {
    private String fromCity;
    private String name;
    private long id;
    private long numberOfOrders;
}

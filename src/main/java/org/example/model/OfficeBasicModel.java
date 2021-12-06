package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OfficeBasicModel {
    private long id;
    private String name;
    private String address;
    private List<String> undergrounds;
    private List<String> workingHours;
    private String restrictionWeight;
}

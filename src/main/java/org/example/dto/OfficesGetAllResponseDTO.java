package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OfficesGetAllResponseDTO {
    private List<Office> offices;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Office {
        private long id;
        private String name;
        private String address;
        private List<String> undergrounds;
        private List<String> workingHours;
        private String restrictionWeight;
    }
}

package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OfficeGetByIdResponseDTO {
    private Office office;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Office {
        private long id;
        private String name;
        private String city;
        private String address;
        private List<String> undergrounds;
        private List<String> workingHours;
        private String restrictionWeight;
        private String description;
        private List<String> paymentMethods;
        private String requisitePhone;
        private String requisiteEmail;
    }
}

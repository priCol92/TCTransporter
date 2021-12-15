package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TariffGetAllResponseDTO {
    private List<Tariff> tariffs;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Tariff {
        private long id;
        private String name;
        private String description;
    }
}


package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRegisterRequestDTO {
    private long tariffId;
    private long routeId;
    private String [] images;
}

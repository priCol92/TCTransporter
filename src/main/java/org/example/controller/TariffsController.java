package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.TariffGetAllResponseDTO;
import org.example.manager.TariffManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tariffs")
@RequiredArgsConstructor
public class TariffsController {
    private final TariffManager manager;

    @GetMapping("/getAll")
    public TariffGetAllResponseDTO getAll() {
        return manager.getAll();
    }
}

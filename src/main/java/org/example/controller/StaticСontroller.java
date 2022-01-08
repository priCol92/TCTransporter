package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.StaticPDOResponseDTO;
import org.example.dto.StaticPPOResponseDTO;
import org.example.dto.StaticRDResponseDTO;
import org.example.manager.StaticManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/static")
@RequiredArgsConstructor
public class StaticСontroller {
    private final StaticManager manager;

    @GetMapping("/getAllRoute")
    public StaticRDResponseDTO routeDistance() {
        return manager.getAllRoute();
    }

    @GetMapping("/popularDepartureOffice")
    public StaticPDOResponseDTO popularDepartureOffice(@RequestParam String city) {
        return manager.popularDepartureOffice(city);
    }

    @GetMapping("/popularPickupOffice")
    public StaticPPOResponseDTO popularPickupOffice(@RequestParam String city) {
        return manager.popularPickupOffice(city);
    }
}

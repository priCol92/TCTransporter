package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.OfficeSaveRequestDTO;
import org.example.dto.OfficeSaveResponseDTO;
import org.example.dto.OfficesGetAllResponseDTO;
import org.example.dto.OfficeGetByIdResponseDTO;
import org.example.manager.OfficeManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offices")
@RequiredArgsConstructor
public class OfficesController {
    private final OfficeManager manager;

    @GetMapping("/getAllInCity")
    public OfficesGetAllResponseDTO getAllInCity(@RequestParam String city) {
        return manager.getAll(city);
    }

    @GetMapping("/getById")
    public OfficeGetByIdResponseDTO getById (@RequestParam long id) {
        return manager.getById(id);
    }

    @PostMapping("/save")
    public OfficeSaveResponseDTO save (@RequestBody OfficeSaveRequestDTO requestDTO){
        return manager.save(requestDTO);
    }

    @PostMapping("/removeById")
    public void removeById(@RequestParam long id) {
        manager.removeById(id);
    }

    @PostMapping("/restoreById")
    public void restoreById(@RequestParam long id) {
        manager.restoreById(id);
    }
}

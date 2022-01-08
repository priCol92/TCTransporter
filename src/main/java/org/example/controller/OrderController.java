package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.OrderGetByIdResponseDTO;
import org.example.dto.OrderRegisterRequestDTO;
import org.example.dto.OrderRegisterResponseDTO;
import org.example.manager.OrderManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderManager manager;

    @PostMapping("/register")
    public OrderRegisterResponseDTO register(@RequestBody OrderRegisterRequestDTO requestDTO) {
        return manager.register(requestDTO);
    }

    @GetMapping("/getById")
    public OrderGetByIdResponseDTO getById(@RequestParam long id) {
        return manager.getById(id);
    }
}


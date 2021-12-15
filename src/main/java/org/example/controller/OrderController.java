package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.OrderRegisterRequestDTO;
import org.example.dto.OrderRegisterResponseDTO;
import org.example.manager.OrderManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderManager manager;

    @PostMapping("/register")
    public OrderRegisterResponseDTO register(@RequestBody OrderRegisterRequestDTO requestDTO) {
        return manager.register(requestDTO);
    }
}


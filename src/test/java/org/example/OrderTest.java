package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Path;

@Testcontainers
@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class OrderTest {
    @Container
    static DockerComposeContainer<?> compose = new DockerComposeContainer<>(
            Path.of("docker-compose.yml").toFile()
    );

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldPerformOrder() throws Exception {
        //register new Order
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/orders/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        // language=JSON
                                        """
                                                {
                                                  "tariffId": 1,
                                                  "routeId": 1,
                                                  "idOfficeFromCity": 3,
                                                  "idOfficeToCity": 4,
                                                  "images": [
                                                    "{{image}}"
                                                  ]
                                                }
                                                """
                                )
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                          "order": {
                                            "id": 1000015,
                                            "tariffName": "Посылочка",
                                            "fromCity": "Kazan",
                                            "toCity": "Moscow",
                                            "time": "Приблизительное время доставки 2 дня.",
                                            "images": [
                                              "{{image}}"
                                            ],
                                            "price": 835
                                          }
                                        }
                                        """
                        )
                );

        //getById
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/orders/getById")
                                .queryParam("id", String.valueOf(1000004))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                          "order": {
                                            "id": 1000004,
                                            "tariffName": "Посылочка",
                                            "fromCity": "Kazan",
                                            "toCity": "Moscow",
                                            "time": "Приблизительное время доставки 2 дня.",
                                            "price": 835,
                                            "images": [
                                              "noImage"
                                            ]
                                          }
                                        }
                                        """
                        )
                );
    }
}

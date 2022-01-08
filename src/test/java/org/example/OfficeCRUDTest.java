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
class OfficeCRUDTest {
    @Container
    static DockerComposeContainer<?> compose = new DockerComposeContainer<>(
            Path.of("docker-compose.yml").toFile()
    );

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldPerformCRUD() throws Exception {
        //getAllInCity
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/offices/getAllInCity")
                                .queryParam("city", String.valueOf("Moscow"))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                           "offices": [
                                             {
                                               "id": 4,
                                               "name": "MOW01",
                                               "address": "пр. Дмитровский, 4",
                                               "undergrounds": [
                                                 "Дмитровская"
                                               ],
                                               "workingHours": [
                                                 "Пн-Пт 10:00-20:00",
                                                 "Сб-Вс 10:00-18:00"
                                               ],
                                               "restrictionWeight": "Вес: от 0 до 35 кг"
                                             },
                                             {
                                               "id": 5,
                                               "name": "MOW02",
                                               "address": "ул. Весенняя, 3, корп. 1",
                                               "undergrounds": [
                                                 "Ховрино",
                                                 "Селигерская"
                                               ],
                                               "workingHours": [
                                                 "Пн-Пт 10:00-20:00",
                                                 "Сб-Вс 10:00-18:00"
                                               ],
                                               "restrictionWeight": "Вес: от 0 до 35 кг"
                                             },
                                             {
                                               "id": 6,
                                               "name": "MOW03",
                                               "address": "ул. Василия Петушкова, 23",
                                               "undergrounds": [
                                                 "Волоколамская"
                                               ],
                                               "workingHours": [
                                                 "Пн-Пт 09:00-21:00",
                                                 "Сб-Вс 09:00-19:00"
                                               ],
                                               "restrictionWeight": "Вес: от 0 до 35 кг"
                                             }
                                           ]
                                         }
                                        """
                        )
                );

        //getById
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/offices/getById")
                                .queryParam("id", String.valueOf(4))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                          "office": {
                                            "id": 4,
                                            "name": "MOW01",
                                            "city": "Moscow",
                                            "address": "пр. Дмитровский, 4",
                                            "undergrounds": [
                                              "Дмитровская"
                                            ],
                                            "workingHours": [
                                              "Пн-Пт 10:00-20:00",
                                              "Сб-Вс 10:00-18:00"
                                            ],
                                            "restrictionWeight": "Вес: от 0 до 35 кг",
                                            "description": "Ближайшие остановки: метро Дмитровская.Пристройка к жилому дому, вход со двора, со стороны магазина «Красное и белое».",
                                            "paymentMethods": [
                                              "Наличные",
                                              "Банковские карты",
                                              "Наложенный платеж"
                                            ],
                                            "requisitePhone": "+7(843)XXX-XX-XX",
                                            "requisiteEmail": "transporter@example.ru"
                                          }
                                        }
                                        """
                        )
                );

        // Exception
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/offices/getById")
                                .queryParam("id", String.valueOf(999))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isNotFound()
                );

        // Save
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/offices/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        // language=JSON
                                        """
                                                {
                                                  "id": 0,
                                                  "name": "KZN04",
                                                  "city": "Kazan",
                                                  "address": "ул. Вишневского, 10",
                                                  "undergrounds": [
                                                    "Суконная слобода"
                                                  ],
                                                  "workingHours": [
                                                    "Пн-Пт 10:00-20:00",
                                                    "Сб-Вс 10:00-18:00"
                                                  ],
                                                  "restrictionWeight": "Вес: от 0 до 35 кг",
                                                  "description": "Ближайшие остановки: Калинина, Вишневского.100 метров от остановки Калинина по направлению в сторону улицы Достоевского, вход через магазин Пятерочка",
                                                  "paymentMethods": [
                                                    "Наличные",
                                                    "Банковские карты",
                                                    "Наложенный платеж"
                                                  ],
                                                  "requisitePhone": "+7(843)XXX-XX-XX",
                                                  "requisiteEmail": "transporter@example.ru"
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
                                          "office": {
                                            "id": 10,
                                            "name": "KZN04",
                                            "city": "Kazan",
                                            "address": "ул. Вишневского, 10",
                                            "undergrounds": [
                                              "Суконная слобода"
                                            ],
                                            "workingHours": [
                                              "Пн-Пт 10:00-20:00",
                                              "Сб-Вс 10:00-18:00"
                                            ],
                                            "restrictionWeight": "Вес: от 0 до 35 кг",
                                            "description": "Ближайшие остановки: Калинина, Вишневского.100 метров от остановки Калинина по направлению в сторону улицы Достоевского, вход через магазин Пятерочка",
                                            "paymentMethods": [
                                              "Наличные",
                                              "Банковские карты",
                                              "Наложенный платеж"
                                            ],
                                            "requisitePhone": "+7(843)XXX-XX-XX",
                                            "requisiteEmail": "transporter@example.ru"
                                          }
                                        }
                                        """
                        )
                );
    }
}

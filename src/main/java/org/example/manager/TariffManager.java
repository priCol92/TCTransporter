package org.example.manager;

import lombok.RequiredArgsConstructor;
import org.example.dto.TariffGetAllResponseDTO;
import org.example.model.TariffModel;
import org.example.rowmapper.TariffRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TariffManager {
    private final NamedParameterJdbcTemplate template;
    private final TariffRowMapper tariffRowMapper;

    public TariffGetAllResponseDTO getAll() {
        final List<TariffModel> items = template.query(
                // language=PostgreSQL
                """
                        SELECT id, name, description FROM tariffs
                        WHERE removed=FALSE
                        ORDER BY id
                        LIMIT 100
                        """,
                tariffRowMapper
        );
        final TariffGetAllResponseDTO responseDTO = new TariffGetAllResponseDTO(new ArrayList<>(items.size()));
        for (TariffModel item : items) {
            responseDTO.getTariffs().add(new TariffGetAllResponseDTO.Tariff(
                    item.getId(),
                    item.getName(),
                    item.getDescription()
            ));
        }
        return responseDTO;
    }
}

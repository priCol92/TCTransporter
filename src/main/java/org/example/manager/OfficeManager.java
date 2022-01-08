package org.example.manager;

import lombok.RequiredArgsConstructor;
import org.example.dto.OfficeGetByIdResponseDTO;
import org.example.dto.OfficeSaveRequestDTO;
import org.example.dto.OfficeSaveResponseDTO;
import org.example.dto.OfficesGetAllResponseDTO;
import org.example.exception.ProductNotFoundException;
import org.example.model.OfficeBasicModel;
import org.example.model.OfficeFullModel;
import org.example.rowmapper.OfficeBasicRowMapper;
import org.example.rowmapper.OfficeFullRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OfficeManager {
    private final NamedParameterJdbcTemplate template;
    private final OfficeBasicRowMapper officeBasicRowMapper;
    private final OfficeFullRowMapper officeFullRowMapper;

    public OfficesGetAllResponseDTO getAll(String city) {
        final List<OfficeBasicModel> items = template.query(
                // language=PostgreSQL
                """
                        SELECT id, name, address, undergrounds, working_hours, restriction_weight FROM offices
                        WHERE removed = FALSE AND city = :city
                        ORDER BY id
                        LIMIT 100
                        """,
                Map.of("city", city),
                officeBasicRowMapper
        );

        final OfficesGetAllResponseDTO responseDTO = new OfficesGetAllResponseDTO(new ArrayList<>(items.size()));
        for (OfficeBasicModel item : items) {
            responseDTO.getOffices().add(new OfficesGetAllResponseDTO.Office(
                    item.getId(),
                    item.getName(),
                    item.getAddress(),
                    item.getUndergrounds(),
                    item.getWorkingHours(),
                    item.getRestrictionWeight()
            ));
        }

        return responseDTO;
    }

    public OfficeGetByIdResponseDTO getById(long id) {
        try {
            final OfficeFullModel item = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id, name, city, address, undergrounds, working_hours, restriction_weight,
                            description, payment_methods, requisite_phone, requisite_email FROM offices
                            WHERE id = :id  AND removed = FALSE
                            """,
                    Map.of("id", id),
                    officeFullRowMapper
            );

            final OfficeGetByIdResponseDTO responseDTO = new OfficeGetByIdResponseDTO(new OfficeGetByIdResponseDTO.Office(
                    item.getId(),
                    item.getName(),
                    item.getCity(),
                    item.getAddress(),
                    item.getUndergrounds(),
                    item.getWorkingHours(),
                    item.getRestrictionWeight(),
                    item.getDescription(),
                    item.getPaymentMethods(),
                    item.getRequisitePhone(),
                    item.getRequisiteEmail()
            ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException(e);
        }
    }

    public OfficeSaveResponseDTO save(OfficeSaveRequestDTO requestDTO) {
        return requestDTO.getId() == 0 ? create(requestDTO) : update(requestDTO);
    }

    private OfficeSaveResponseDTO create(OfficeSaveRequestDTO requestDTO) {
        final OfficeFullModel item = template.queryForObject(
                // language=PostgreSQL
                """
                        INSERT INTO offices (name, city, address, undergrounds,
                        working_hours, restriction_weight, description,
                        payment_methods, requisite_phone, requisite_email) 
                        VALUES (:name, :city, :address, :undergrounds,
                        :workingHours, :restrictionWeight, :description,
                        :paymentMethods, :requisitePhone, :requisiteEmail)
                        RETURNING id, name, city, address, undergrounds,
                        working_hours, restriction_weight, description,
                        payment_methods, requisite_phone, requisite_email
                        """,
                Map.of(
                        "name", requestDTO.getName(),
                        "city", requestDTO.getCity(),
                        "address", requestDTO.getAddress(),
                        "undergrounds", requestDTO.getUndergrounds().toArray(new String[]{}),
                        "workingHours", requestDTO.getWorkingHours().toArray(new String[]{}),
                        "restrictionWeight", requestDTO.getRestrictionWeight(),
                        "description", requestDTO.getDescription(),
                        "paymentMethods", requestDTO.getPaymentMethods().toArray(new String[]{}),
                        "requisitePhone", requestDTO.getRequisitePhone(),
                        "requisiteEmail", requestDTO.getRequisiteEmail()
                ),
                officeFullRowMapper
        );

        final OfficeSaveResponseDTO responseDTO = new OfficeSaveResponseDTO(new OfficeSaveResponseDTO.Office(
                item.getId(),
                item.getName(),
                item.getCity(),
                item.getAddress(),
                item.getUndergrounds(),
                item.getWorkingHours(),
                item.getRestrictionWeight(),
                item.getDescription(),
                item.getPaymentMethods(),
                item.getRequisitePhone(),
                item.getRequisiteEmail()
        ));

        return responseDTO;
    }

    private OfficeSaveResponseDTO update(OfficeSaveRequestDTO requestDTO) {
        try {
            final OfficeFullModel item = template.queryForObject(
                    // language=PostgreSQL
                    """
                            UPDATE offices SET name = :name, address = :address, undergrounds = :undergrounds,
                            working_hours = :workingHours, description =:description
                            WHERE id = :id AND removed = FALSE
                            RETURNING id, name, city, address, undergrounds,
                            working_hours, restriction_weight, description,
                            payment_methods, requisite_phone, requisite_email
                            """,
                    Map.of(
                            "id", requestDTO.getId(),
                            "name", requestDTO.getName(),
                            "city", requestDTO.getCity(),
                            "address", requestDTO.getAddress(),
                            "undergrounds", requestDTO.getUndergrounds().toArray(new String[]{}),
                            "workingHours", requestDTO.getWorkingHours().toArray(new String[]{}),
                            "restrictionWeight", requestDTO.getRestrictionWeight(),
                            "description", requestDTO.getDescription(),
                            "paymentMethods", requestDTO.getPaymentMethods().toArray(new String[]{})
                    ),
                    officeFullRowMapper
            );

            final OfficeSaveResponseDTO responseDTO = new OfficeSaveResponseDTO(new OfficeSaveResponseDTO.Office(
                    item.getId(),
                    item.getName(),
                    item.getCity(),
                    item.getAddress(),
                    item.getUndergrounds(),
                    item.getWorkingHours(),
                    item.getRestrictionWeight(),
                    item.getDescription(),
                    item.getPaymentMethods(),
                    item.getRequisitePhone(),
                    item.getRequisiteEmail()
            ));

            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException(e);
        }
    }

    public void removeById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE offices SET removed = TRUE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new ProductNotFoundException("office with id " + id + " not found");
        }
    }

    public void restoreById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE offices SET removed = FALSE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new ProductNotFoundException("office with id " + id + " not found");
        }
    }
}


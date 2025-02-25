package ru.astongroup.carbooking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CarRequestDto {

    @NotNull
    private Long id;

    private BigDecimal costPerMinute;
}

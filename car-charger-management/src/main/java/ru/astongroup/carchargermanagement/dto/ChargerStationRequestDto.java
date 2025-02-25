package ru.astongroup.carchargermanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChargerStationRequestDto {
    @NotNull
    private String nameStation;

    @NotNull
    private String addressStation;

    @NotNull
    private Boolean isAvailableStation;

    @NotNull
    private Float latitude;

    @NotNull
    private Float longitude;
}

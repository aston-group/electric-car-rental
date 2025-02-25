package ru.astongroup.carchargermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargerStationResponseDto {

    private Long stationId;
    private String nameStation;
    private String addressStation;
    private Boolean isAvailableStation;
    private Float latitude;
    private Float longitude;
}

package ru.astongroup.carchargermanagement.mapper;

import ru.astongroup.carchargermanagement.dto.ChargerStationRequestDto;
import ru.astongroup.carchargermanagement.dto.ChargerStationResponseDto;
import ru.astongroup.carchargermanagement.entity.ChargerStation;

public class ChargerStationMapper {
    private ChargerStationMapper() {
    }

    public static ChargerStation toChargerStationDto(ChargerStationRequestDto chargerStationRequestDto) {
/*        ChargerStation chargerStation = new ChargerStation();
        chargerStation.setNameStation(chargerStationRequestDto.getNameStation());
        chargerStation.setAddressStation(chargerStationRequestDto.getAddressStation());
        chargerStation.setIsAvailableStation(chargerStationRequestDto.getIsAvailableStation());
        chargerStation.setLatitude(chargerStationRequestDto.getLatitude());
        chargerStation.setLongitude(chargerStationRequestDto.getLongitude());
        return chargerStation;*/
        return ChargerStation.builder()
                .nameStation(chargerStationRequestDto.getNameStation())
                .addressStation(chargerStationRequestDto.getAddressStation())
                .isAvailableStation(chargerStationRequestDto.getIsAvailableStation())
                .latitude(chargerStationRequestDto.getLatitude())
                .longitude(chargerStationRequestDto.getLongitude())
                .build();
    }

    public static ChargerStationResponseDto toChargerStationResponseDto(ChargerStation chargerStation) {
        return ChargerStationResponseDto.builder()
                .stationId(chargerStation.getStationId())
                .nameStation(chargerStation.getNameStation())
                .addressStation(chargerStation.getAddressStation())
                .isAvailableStation(chargerStation.getIsAvailableStation())
                .latitude(chargerStation.getLatitude())
                .longitude(chargerStation.getLongitude())
                .build();
    }
}

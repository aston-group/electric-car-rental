package ru.astongroup.carchargermanagement.service;

import ru.astongroup.carchargermanagement.dto.ChargerStationRequestDto;
import ru.astongroup.carchargermanagement.dto.ChargerStationResponseDto;

import java.util.List;


public interface ChargerStationService {
    ChargerStationResponseDto create(ChargerStationRequestDto chargingStationRequestDto);
    List<ChargerStationResponseDto> findAll();
    ChargerStationResponseDto findById(Long id);
    void delete(Long id);
    void update(Long id, ChargerStationRequestDto chargerStationRequestDto);

    ChargerStationResponseDto startCharging(Long stationId, Long carId);
    ChargerStationResponseDto endCharging(Long stationId, Long carId);

}

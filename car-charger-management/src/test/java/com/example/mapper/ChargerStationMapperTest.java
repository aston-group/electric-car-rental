package com.example.mapper;

import org.junit.jupiter.api.Test;
import ru.astongroup.carchargermanagement.dto.ChargerStationRequestDto;
import ru.astongroup.carchargermanagement.dto.ChargerStationResponseDto;
import ru.astongroup.carchargermanagement.entity.ChargerStation;
import ru.astongroup.carchargermanagement.mapper.ChargerStationMapper;

import static org.junit.jupiter.api.Assertions.*;

public class ChargerStationMapperTest {
    @Test
    void testToChargerStationDto() {
        // Подготовка данных
        ChargerStationRequestDto requestDto = new ChargerStationRequestDto();
        requestDto.setNameStation("Station 1");
        requestDto.setAddressStation("Address 1");
        requestDto.setIsAvailableStation(true);
        requestDto.setLatitude(55.7558F);
        requestDto.setLongitude(37.6176F);

        // Вызов метода
        ChargerStation chargerStation = ChargerStationMapper.toChargerStationDto(requestDto);

        // Проверки
        assertNotNull(chargerStation);
        assertEquals("Station 1", chargerStation.getNameStation());
        assertEquals("Address 1", chargerStation.getAddressStation());
        assertTrue(chargerStation.getIsAvailableStation());
        assertEquals(55.7558F, chargerStation.getLatitude());
        assertEquals(37.6176F, chargerStation.getLongitude());
    }

    @Test
    void testToChargerStationResponseDto() {
        // Подготовка данных
        ChargerStation chargerStation = new ChargerStation();
        chargerStation.setStationId(1L);
        chargerStation.setNameStation("Station 1");
        chargerStation.setAddressStation("Address 1");
        chargerStation.setIsAvailableStation(true);
        chargerStation.setLatitude(55.7558F);
        chargerStation.setLongitude(37.6176F);

        // Вызов метода
        ChargerStationResponseDto responseDto = ChargerStationMapper.toChargerStationResponseDto(chargerStation);

        // Проверки
        assertNotNull(responseDto);
        assertEquals(1L, responseDto.getStationId());
        assertEquals("Station 1", responseDto.getNameStation());
        assertEquals("Address 1", responseDto.getAddressStation());
        assertTrue(responseDto.getIsAvailableStation());
        assertEquals(55.7558F, responseDto.getLatitude());
        assertEquals(37.6176F, responseDto.getLongitude());
    }
}

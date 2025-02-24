package com.example.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astongroup.carchargermanagement.dto.ChargerStationRequestDto;
import ru.astongroup.carchargermanagement.dto.ChargerStationResponseDto;
import ru.astongroup.carchargermanagement.entity.ChargerStation;
import ru.astongroup.carchargermanagement.repository.ChargerStationRepository;
import ru.astongroup.carchargermanagement.service.ChargerStationServiceImpl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ChargerStationServiceImplTest {

    @Mock
    private ChargerStationRepository chargerStationRepository;

    @InjectMocks
    private ChargerStationServiceImpl chargerStationService;

    @Test
    void testCreateChargerStation() {
        ChargerStationRequestDto requestDto = new ChargerStationRequestDto();
        requestDto.setNameStation("Station 1");
        requestDto.setAddressStation("Address 1");
        requestDto.setIsAvailableStation(true);
        requestDto.setLatitude(55.7558F);
        requestDto.setLongitude(37.6176F);

        ChargerStation savedStation = new ChargerStation();
        savedStation.setNameStation("Station 1");
        savedStation.setAddressStation("Address 1");
        savedStation.setIsAvailableStation(true);
        savedStation.setLatitude(55.7558F);
        savedStation.setLongitude(37.6176F);

        when(chargerStationRepository.save(any(ChargerStation.class))).thenAnswer(invocation -> {
            ChargerStation station = invocation.getArgument(0);
            station.setStationId(1L); // Имитируем присвоение ID базой данных
            return station;
        });

        ChargerStationResponseDto responseDto = chargerStationService.create(requestDto);

        assertNotNull(responseDto);
        assertEquals(1L, responseDto.getStationId()); // Проверяем, что ID был присвоен
        assertEquals("Station 1", responseDto.getNameStation());
        assertEquals("Address 1", responseDto.getAddressStation());
        assertTrue(responseDto.getIsAvailableStation());
        assertEquals(55.7558F, responseDto.getLatitude());
        assertEquals(37.6176F, responseDto.getLongitude());

        verify(chargerStationRepository, times(1)).save(any(ChargerStation.class));
    }
}

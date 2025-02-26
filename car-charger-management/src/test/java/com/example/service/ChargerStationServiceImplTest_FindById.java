package com.example.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astongroup.carchargermanagement.dto.ChargerStationResponseDto;
import ru.astongroup.carchargermanagement.entity.ChargerStation;
import ru.astongroup.carchargermanagement.exception.DataNotFoundException;
import ru.astongroup.carchargermanagement.repository.ChargerStationRepository;
import ru.astongroup.carchargermanagement.service.ChargerStationServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class ChargerStationServiceImplTest_FindById {
    @Mock
    private ChargerStationRepository chargerStationRepository;

    @InjectMocks
    private ChargerStationServiceImpl chargerStationService;

    @Test
    void testFindById_Success() {
        // Подготовка данных
        Long stationId = 1L;
        ChargerStation station = new ChargerStation();
        station.setStationId(stationId);
        station.setNameStation("Station 1");
        station.setAddressStation("Address 1");
        station.setIsAvailableStation(true);
        station.setLatitude(55.7558F);
        station.setLongitude(37.6176F);

        // Мокируем вызов репозитория
        when(chargerStationRepository.findById(stationId)).thenReturn(Optional.of(station));

        // Вызов метода
        ChargerStationResponseDto responseDto = chargerStationService.findById(stationId);

        // Проверки
        assertNotNull(responseDto);
        assertEquals(stationId, responseDto.getStationId());
        assertEquals("Station 1", responseDto.getNameStation());
        assertEquals("Address 1", responseDto.getAddressStation());
        assertTrue(responseDto.getIsAvailableStation());
        assertEquals(55.7558F, responseDto.getLatitude());
        assertEquals(37.6176F, responseDto.getLongitude());

        // Проверка, что метод findById был вызван ровно один раз
        verify(chargerStationRepository, times(1)).findById(stationId);
    }

    @Test
    void testFindById_NotFound() {
        // Подготовка данных
        Long stationId = 999L;

        // Мокируем вызов репозитория (возвращаем пустой Optional)
        when(chargerStationRepository.findById(stationId)).thenReturn(Optional.empty());

        // Вызов метода и проверка исключения
        assertThrows(DataNotFoundException.class, () -> {
            chargerStationService.findById(stationId);
        });

        // Проверка, что метод findById был вызван ровно один раз
        verify(chargerStationRepository, times(1)).findById(stationId);
    }

}

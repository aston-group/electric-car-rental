package com.example.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astongroup.carchargermanagement.dto.ChargerStationRequestDto;
import ru.astongroup.carchargermanagement.entity.ChargerStation;
import ru.astongroup.carchargermanagement.repository.ChargerStationRepository;
import ru.astongroup.carchargermanagement.service.ChargerStationServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ChargerStationServiceImplTest_Update {
    @Mock
    private ChargerStationRepository chargerStationRepository;

    @InjectMocks
    private ChargerStationServiceImpl chargerStationService;

    @Test
    void testUpdate_Success() {
        // Подготовка данных
        Long stationId = 1L;
        ChargerStationRequestDto requestDto = new ChargerStationRequestDto();
        requestDto.setNameStation("Updated Station");
        requestDto.setAddressStation("Updated Address");
        requestDto.setIsAvailableStation(false);
        requestDto.setLatitude(56.7558F);
        requestDto.setLongitude(38.6176F);

        ChargerStation existingStation = new ChargerStation();
        existingStation.setStationId(stationId);
        existingStation.setNameStation("Old Station");
        existingStation.setAddressStation("Old Address");
        existingStation.setIsAvailableStation(true);
        existingStation.setLatitude(55.7558F);
        existingStation.setLongitude(37.6176F);

        // Мокируем вызов findById, чтобы вернуть существующую станцию
        when(chargerStationRepository.findById(stationId)).thenReturn(Optional.of(existingStation));

        // Вызов метода
        chargerStationService.update(stationId, requestDto);

        // Проверка, что поля станции были обновлены
        assertEquals("Updated Station", existingStation.getNameStation());
        assertEquals("Updated Address", existingStation.getAddressStation());
        assertFalse(existingStation.getIsAvailableStation());
        assertEquals(56.7558F, existingStation.getLatitude());
        assertEquals(38.6176F, existingStation.getLongitude());

        // Проверка, что метод save был вызван ровно один раз
        verify(chargerStationRepository, times(1)).save(existingStation);
    }

    @Test
    void testUpdate_NotFound() {
        // Подготовка данных
        Long stationId = 999L;
        ChargerStationRequestDto requestDto = new ChargerStationRequestDto();
        requestDto.setNameStation("Updated Station");
        requestDto.setAddressStation("Updated Address");
        requestDto.setIsAvailableStation(false);
        requestDto.setLatitude(56.7558F);
        requestDto.setLongitude(38.6176F);

        // Мокируем вызов findById, чтобы вернуть пустой Optional (станция не существует)
        when(chargerStationRepository.findById(stationId)).thenReturn(Optional.empty());

        // Вызов метода
        chargerStationService.update(stationId, requestDto);

        // Проверка, что метод save не был вызван
        verify(chargerStationRepository, never()).save(any(ChargerStation.class));
    }
}

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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

public class ChargerStationServiceImplTest_FindAll {

    @Mock
    private ChargerStationRepository chargerStationRepository;

    @InjectMocks
    private ChargerStationServiceImpl chargerStationService;

    @Test
    void testFindAllChargerStations() {
        // Создаем список зарядных станций, которые будут возвращены репозиторием
        List<ChargerStation> stations = new ArrayList<>();
        ChargerStation station1 = new ChargerStation();
        station1.setStationId(1L);
        station1.setNameStation("Station 1");
        station1.setAddressStation("Address 1");
        station1.setIsAvailableStation(true);
        station1.setLatitude(55.7558F);
        station1.setLongitude(37.6176F);

        ChargerStation station2 = new ChargerStation();
        station2.setStationId(2L);
        station2.setNameStation("Station 2");
        station2.setAddressStation("Address 2");
        station2.setIsAvailableStation(false);
        station2.setLatitude(56.7558F);
        station2.setLongitude(38.6176F);

        stations.add(station1);
        stations.add(station2);

        // Мокируем вызов репозитория
        when(chargerStationRepository.findAll()).thenReturn(stations);

        // Вызываем метод findAll сервиса
        List<ChargerStationResponseDto> responseDtos = chargerStationService.findAll();

        // Проверяем, что список не пустой и содержит ожидаемое количество элементов
        assertNotNull(responseDtos);
        assertEquals(2, responseDtos.size());

        // Проверяем данные первой станции
        ChargerStationResponseDto responseDto1 = responseDtos.get(0);
        assertEquals(1L, responseDto1.getStationId());
        assertEquals("Station 1", responseDto1.getNameStation());
        assertEquals("Address 1", responseDto1.getAddressStation());
        assertTrue(responseDto1.getIsAvailableStation());
        assertEquals(55.7558F, responseDto1.getLatitude());
        assertEquals(37.6176F, responseDto1.getLongitude());

        // Проверяем данные второй станции
        ChargerStationResponseDto responseDto2 = responseDtos.get(1);
        assertEquals(2L, responseDto2.getStationId());
        assertEquals("Station 2", responseDto2.getNameStation());
        assertEquals("Address 2", responseDto2.getAddressStation());
        assertFalse(responseDto2.getIsAvailableStation());
        assertEquals(56.7558F, responseDto2.getLatitude());
        assertEquals(38.6176F, responseDto2.getLongitude());

        // Проверяем, что метод findAll был вызван ровно один раз
        verify(chargerStationRepository, times(1)).findAll();
    }
}

package com.example.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astongroup.carchargermanagement.exception.DataNotFoundException;
import ru.astongroup.carchargermanagement.repository.ChargerStationRepository;
import ru.astongroup.carchargermanagement.service.ChargerStationServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChargerStationServiceImplTest_Delete {
    @Mock
    private ChargerStationRepository chargerStationRepository;

    @InjectMocks
    private ChargerStationServiceImpl chargerStationService;

    @Test
    void testDelete_Success() {
        // Подготовка данных
        Long stationId = 1L;

        // Мокируем вызов existsById, чтобы вернуть true (станция существует)
        when(chargerStationRepository.existsById(stationId)).thenReturn(true);

        // Вызов метода
        chargerStationService.delete(stationId);

        // Проверка, что метод deleteById был вызван ровно один раз
        verify(chargerStationRepository, times(1)).deleteById(stationId);
    }

    @Test
    void testDelete_NotFound() {
        // Подготовка данных
        Long stationId = 999L;

        // Мокируем вызов existsById, чтобы вернуть false (станция не существует)
        when(chargerStationRepository.existsById(stationId)).thenReturn(false);

        // Вызов метода и проверка исключения
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            chargerStationService.delete(stationId);
        });

        // Проверка сообщения исключения
        assertEquals("Charger station not found" + stationId, exception.getMessage());

        // Проверка, что метод deleteById не был вызван
        verify(chargerStationRepository, never()).deleteById(stationId);
    }
}

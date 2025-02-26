package ru.astongroup.carbooking.service;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astongroup.carbooking.client.CarClient;
import ru.astongroup.carbooking.client.UserClient;
import ru.astongroup.carbooking.dto.*;
import ru.astongroup.carbooking.entity.Booking;
import ru.astongroup.carbooking.entity.Status;
import ru.astongroup.carbooking.mapper.BookingMapper;
import ru.astongroup.carbooking.repository.BookingRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private CarClient carClient;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Booking booking;
    private BookingReqCreateDto bookingReqCreateDto;
    private BookingResponseDTO bookingResponseDTO;
    private CarResponseDTO carResponseDTO;

    @BeforeEach
    void setUp() {
        bookingReqCreateDto = new BookingReqCreateDto(1L, 1L, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10));

        // Создаем мок бронирования
        booking = new Booking(1L, 1L, 1L, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10), Status.PENDING, 10.0);

        bookingResponseDTO = new BookingResponseDTO(1L, 1L, 1L, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10), Status.PENDING, 10.0);

        // Создаем мок машины
        carResponseDTO = CarResponseDTO.builder()
                .id(1L)
                .costPerMinute(BigDecimal.valueOf(5.0))
                .build();
    }

    @Test
    void createBooking_Success() {
        when(userClient.getUserById(anyLong())).thenReturn(Optional.of(UserRequestDto.builder().id(1L).build()));
        when(carClient.getCarById(1L)).thenReturn(Optional.of(carResponseDTO));
        when(bookingMapper.toBookingEntity(any(BookingReqCreateDto.class))).thenReturn(booking);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(bookingMapper.toBookingResponseDto(any(Booking.class))).thenReturn(bookingResponseDTO);

        BookingResponseDTO result = bookingService.createBooking(bookingReqCreateDto);

        assertNotNull(result);
        assertEquals(bookingResponseDTO.getId(), result.getId());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void updateBooking_Success() {
        // Мокируем вызовы
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(carClient.getCarById(1L)).thenReturn(Optional.of(carResponseDTO));
        when(bookingMapper.toBookingResponseDto(any(Booking.class))).thenReturn(bookingResponseDTO);

        // Данные для обновления
        BookingReqUpdateDto updateDto = new BookingReqUpdateDto(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10)
        );

        // Вызываем тестируемый метод
        BookingResponseDTO result = bookingService.updateBooking(1L, updateDto);

        // Проверяем, что обновление прошло успешно
        assertNotNull(result);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void cancelBooking_Success() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        bookingService.cancelBooking(1L);

        assertEquals(Status.CANCELED, booking.getStatus());
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void getBookingPrice_Success() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        double price = bookingService.getBookingPrice(1L);
        assertEquals(10.0, price);
    }

    @Test
    void findAll_Success() {
        when(bookingRepository.findAll()).thenReturn(List.of(booking));
        when(bookingMapper.toBookingResponseDto(any(Booking.class))).thenReturn(bookingResponseDTO);

        Collection<BookingResponseDTO> results = bookingService.findAll();
        assertFalse(results.isEmpty());
    }

}
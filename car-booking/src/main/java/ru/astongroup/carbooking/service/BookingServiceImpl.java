package ru.astongroup.carbooking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astongroup.carbooking.client.CarClient;
import ru.astongroup.carbooking.client.NotificationClient;
import ru.astongroup.carbooking.client.UserClient;
import ru.astongroup.carbooking.dto.BookingReqCreateDto;
import ru.astongroup.carbooking.dto.BookingReqUpdateDto;
import ru.astongroup.carbooking.dto.BookingResponseDTO;
import ru.astongroup.carbooking.dto.CarResponseDTO;
import ru.astongroup.carbooking.dto.NotificationCreateDto;
import ru.astongroup.carbooking.entity.Booking;
import ru.astongroup.carbooking.entity.NotificationType;
import ru.astongroup.carbooking.entity.Status;
import ru.astongroup.carbooking.exception.BookingException;
import ru.astongroup.carbooking.mapper.BookingMapper;
import ru.astongroup.carbooking.repository.BookingRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final CarClient carClient;
    private final UserClient userClient;
    private final NotificationClient notificationClient;

    //думаю нужно получать цену за минуту с таблицы машины, когда админ будет ее добавлять
//    private static final double PRICE_PER_MINUTE = 1; // Цена 1$ за минуту

    //1 создание брони
    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingReqCreateDto bookingReqCreateDto) {

        userClient.getUserById(bookingReqCreateDto.getUserId())
                .orElseThrow(() -> {
                    log.warn("Пользователь с id = {} не найден", bookingReqCreateDto.getUserId());
                    return new RuntimeException(String.format("Пользователь с id = %d не найден",
                            bookingReqCreateDto.getUserId()));
                });

        carClient.getCarById(bookingReqCreateDto.getCarId())
                .orElseThrow(() -> {
                    log.warn("Машина с id = {} не найдена", bookingReqCreateDto.getCarId());
                    return new RuntimeException(String.format("Машина с id = %d не найдена",
                            bookingReqCreateDto.getCarId()));
                });

        Booking bookingEntity = bookingMapper.toBookingEntity(bookingReqCreateDto);
        bookingEntity.setStatus(Status.PENDING);
        bookingEntity.setPrice(calculatePrice(
                bookingReqCreateDto.getCarId(),
                bookingReqCreateDto.getStartTime(),
                bookingReqCreateDto.getEndTime()));
        Booking savedBooking = bookingRepository.save(bookingEntity);
        notificationClient.sendNotification(NotificationCreateDto.builder()
                .userId(bookingReqCreateDto.getUserId())
                .bookingId(savedBooking.getId())
                .notificationType(NotificationType.NEW_BOOKING)
                .build());

        return bookingMapper.toBookingResponseDto(savedBooking);

    }

    //2 редактирование брони
    @Override
    @Transactional
    public BookingResponseDTO updateBooking(Long id, BookingReqUpdateDto bookingReqUpdateDto) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Бронирование не найдено при попытке обновления: id={}", id);
                    return new BookingException(BookingException.BOOKING_NOT_FOUND);
                });

        if (existingBooking.getStatus() == Status.CANCELED) {
            log.error("Бронирование имеет статус закрыто: id={}", id);
            throw new BookingException(BookingException.CANCELLED_BOOKING);
        }

        existingBooking.setCarId(bookingReqUpdateDto.getCarId());
        existingBooking.setStartTime(bookingReqUpdateDto.getStartTime());
        existingBooking.setEndTime(bookingReqUpdateDto.getEndTime());
        existingBooking.setPrice(calculatePrice(
                bookingReqUpdateDto.getCarId(),
                bookingReqUpdateDto.getStartTime(),
                bookingReqUpdateDto.getEndTime()));

        Booking updatedBooking = bookingRepository.save(existingBooking);

        return bookingMapper.toBookingResponseDto(updatedBooking);
    }

    //3 отмена брони
    @Override
    @Transactional
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Бронирование не найдено при попытке закрытия: id={}", id);
                    return new BookingException(BookingException.BOOKING_NOT_FOUND);
                });

        booking.setStatus(Status.CANCELED);
        notificationClient.sendNotification(NotificationCreateDto.builder()
                .bookingId(booking.getId())
                .userId(booking.getUserId())
                .notificationType(NotificationType.REJECTED_BOOKING)
                .build());
        bookingRepository.save(booking);
    }

    //4 расчёт стоимости
    @Override
    @Transactional(readOnly = true)
    public double getBookingPrice(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Бронирование не найдено при попытке получения стоимости: id={}", id);
                    return new BookingException(BookingException.BOOKING_NOT_FOUND);
                });
        return booking.getPrice();
    }

    //5 получить бронирования по ИД юзера
    @Override
    @Transactional(readOnly = true)
    public Collection<BookingResponseDTO> getBookingsByUserId(Long userId) {
        log.info("Получение бронирований для userId={}", userId);
        Collection<Booking> bookingsEntity = bookingRepository.findByUserId(userId);

        Collection<BookingResponseDTO> bookingsDto = new ArrayList<>();
        for (Booking booking : bookingsEntity) {
            BookingResponseDTO bookingResponseDTO = bookingMapper.toBookingResponseDto(booking);
            bookingsDto.add(bookingResponseDTO);
        }

        return bookingsDto;
    }

    //6 подтверждение бронирования администратором
    @Override
    @Transactional
    public BookingResponseDTO confirmBooking(Long bookingId) {
        log.info("Попытка подтверждения бронирования bookingId={}", bookingId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    log.error("Бронирование не найдено при попытке подтверждения: bookingId={}", bookingId);
                    return new BookingException(BookingException.BOOKING_NOT_FOUND);
                });

        booking.setStatus(Status.CONFIRMED);
        notificationClient.sendNotification(NotificationCreateDto.builder()
                .bookingId(booking.getId())
                .userId(booking.getUserId())
                .notificationType(NotificationType.CONFIRMED_BOOKING)
                .build());
        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapper.toBookingResponseDto(savedBooking);
    }

    //7 получить все букинги, но это только надо будет для админа опцию сделать
    @Override
    @Transactional(readOnly = true)
    public Collection<BookingResponseDTO> findAll() {
        Collection<Booking> bookingRepositoryAll = bookingRepository.findAll();

        Collection<BookingResponseDTO> bookingsResponseDTO = new ArrayList<>();
        for (Booking booking : bookingRepositoryAll) {
            BookingResponseDTO bookingResponseDTO = bookingMapper.toBookingResponseDto(booking);
            bookingsResponseDTO.add(bookingResponseDTO);
        }
        return bookingsResponseDTO;
    }

    //здесь нужно брать стоимость с таблицы машин стоимость за минуту когда админ создавать будет
    private double calculatePrice(Long carId, LocalDateTime start, LocalDateTime end) {
        long minutes = Duration.between(start, end).toMinutes();

        Optional<CarResponseDTO> optionalCar = carClient.getCarById(carId);

        if (optionalCar.isEmpty()) {
            throw new RuntimeException("Машина с id " + carId + " не найдена");
        }

        CarResponseDTO carResponseDTO = optionalCar.get();
        BigDecimal costPerMinute = carResponseDTO.getCostPerMinute();

        return costPerMinute.multiply(BigDecimal.valueOf(minutes)).doubleValue();
    }
}

package ru.astongroup.carbooking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astongroup.carbooking.dto.BookingDto;
import ru.astongroup.carbooking.entity.Booking;
import ru.astongroup.carbooking.entity.Status;
import ru.astongroup.carbooking.exception.BookingException;
import ru.astongroup.carbooking.mapper.BookingMapper;
import ru.astongroup.carbooking.repository.BookingRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    //думаю нужно получать цену за минуту с таблицы машины, когда админ будет ее добавлять
    private static final double PRICE_PER_MINUTE = 1; // Цена 1$ за минуту

    //1 создание брони
    @Transactional
    public BookingDto createBooking(BookingDto bookingDto) {
        bookingDto.setStatus(Status.PENDING);
        bookingDto.setPrice(calculatePrice(bookingDto.getStartTime(), bookingDto.getEndTime()));

        Booking bookingEntity = BookingMapper.toBookingEntity(bookingDto);
        Booking savedBooking = bookingRepository.save(bookingEntity);

        return BookingMapper.toBookingDto(savedBooking);
    }

    //2 редактирование брони
    @Transactional
    public BookingDto updateBooking(Long id, BookingDto updatedBookingDto) {

        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Бронирование не найдено при попытке обновления: id={}", id);
                    return new BookingException(BookingException.BOOKING_NOT_FOUND);
                });

        if (existingBooking.getStatus() == Status.CANCELED) {
            log.error("Бронирование имеет статус закрыто: id={}", id);
            throw new BookingException(BookingException.CANCELLED_BOOKING);
        }

        existingBooking.setStartTime(updatedBookingDto.getStartTime());
        existingBooking.setEndTime(updatedBookingDto.getEndTime());
        existingBooking.setPrice(calculatePrice(updatedBookingDto.getStartTime(), updatedBookingDto.getEndTime()));

        Booking updatedBooking = bookingRepository.save(existingBooking);

        return BookingMapper.toBookingDto(updatedBooking);
    }

    //3 отмена брони
    @Transactional
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Бронирование не найдено при попытке закрытия: id={}", id);
                    return new BookingException(BookingException.BOOKING_NOT_FOUND);
                });

        booking.setStatus(Status.CANCELED);
        bookingRepository.save(booking);
    }

    //4 расчёт стоимости
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
    @Transactional(readOnly = true)
    public Collection<BookingDto> getBookingsByUserId(Long userId) {
        log.info("Получение бронирований для userId={}", userId);

        Collection<Booking> bookingsEntity = bookingRepository.findByUserId(userId);
        Collection<BookingDto> bookingsDto = new ArrayList<>();

        for (Booking booking : bookingsEntity) {
            BookingDto bookingDto = BookingMapper.toBookingDto(booking);
            bookingsDto.add(bookingDto);
        }

        return bookingsDto;
    }

    //6 подтверждение бронирования администратором
    @Transactional
    public BookingDto confirmBooking(Long bookingId) {
        log.info("Попытка подтверждения бронирования bookingId={}", bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    log.error("Бронирование не найдено при попытке подтверждения: bookingId={}", bookingId);
                    return new BookingException(BookingException.BOOKING_NOT_FOUND);
                });

        booking.setStatus(Status.CONFIRMED);
        Booking savedBooking = bookingRepository.save(booking);

        return BookingMapper.toBookingDto(savedBooking);
    }

    //7 получить все букинги, но это только надо будет для админа опцию сделать
    @Transactional(readOnly = true)
    public Collection<BookingDto> findAll() {
        Collection<Booking> bookingRepositoryAll = bookingRepository.findAll();
        Collection<BookingDto> bookingDtoList = new ArrayList<>();
        for (Booking booking : bookingRepositoryAll) {
            BookingDto bookingDto = BookingMapper.toBookingDto(booking);
            bookingDtoList.add(bookingDto);
        }
        return bookingDtoList;
    }

    //здесь нужно брать стоимость с таблицы машин стоимость за минуту когда админ создавать будет
    private double calculatePrice(LocalDateTime start, LocalDateTime end) {
        long minutes = Duration.between(start, end).toMinutes();
        return minutes * PRICE_PER_MINUTE;
    }
}

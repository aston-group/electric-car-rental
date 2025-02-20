package ru.astongroup.carbooking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.astongroup.carbooking.dto.BookingDto;
import ru.astongroup.carbooking.service.BookingService;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    //1 создание брони
    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookingDto bookingDto) {
        return ResponseEntity.ok(bookingService.createBooking(bookingDto));
    }

    //2 редактирование брони
    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> updateBooking(
            @PathVariable(name = "id") Long id,
            @RequestBody BookingDto bookingDto) {
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDto));
    }

    //3 отмена брони
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    //4 получить стоимость по ИД
    @GetMapping("/{id}/price")
    public ResponseEntity<Double> getBookingPrice(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingPrice(id));
    }

    //5 получить бронирования по ИД юзера
    @GetMapping("/user/{userId}")
    public ResponseEntity<Collection<BookingDto>> getBookingsByUserId(@PathVariable Long userId) {
        log.info("Запрос на получение бронирований для userId={}", userId);
        Collection<BookingDto> bookingsDto = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(bookingsDto);
    }

    //6 подтверждение бронирования администратором
    @PutMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingDto> confirmBooking(@PathVariable Long bookingId) {
        log.info("Запрос на подтверждение бронирования bookingId={}", bookingId);
        BookingDto confirmedBooking = bookingService.confirmBooking(bookingId);
        return ResponseEntity.ok(confirmedBooking);
    }

    //7 получить все букинги, но это только надо будет для админа опцию сделать
    @GetMapping()
    public Collection<BookingDto> findAll() {
        return bookingService.findAll();
    }
}
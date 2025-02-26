package ru.astongroup.carbooking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.astongroup.carbooking.dto.BookingReqCreateDto;
import ru.astongroup.carbooking.dto.BookingReqUpdateDto;
import ru.astongroup.carbooking.dto.BookingResponseDTO;
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
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingReqCreateDto bookingReqCreateDto) {
        return ResponseEntity.ok(bookingService.createBooking(bookingReqCreateDto));
    }

    //2 редактирование брони
    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> updateBooking(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody BookingReqUpdateDto bookingReqUpdateDto) {
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingReqUpdateDto));
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
    public ResponseEntity<Collection<BookingResponseDTO>> getBookingsByUserId(@PathVariable Long userId) {
        log.info("Запрос на получение бронирований для userId={}", userId);
        Collection<BookingResponseDTO> bookingsResponseDto = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(bookingsResponseDto);
    }

    //6 подтверждение бронирования администратором
    @PutMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingResponseDTO> confirmBooking(@PathVariable Long bookingId) {
        log.info("Запрос на подтверждение бронирования bookingId={}", bookingId);
        BookingResponseDTO confirmedBooking = bookingService.confirmBooking(bookingId);
        return ResponseEntity.ok(confirmedBooking);
    }

    //7 получить все букинги, но это только надо будет для админа опцию сделать
    @GetMapping()
    public Collection<BookingResponseDTO> findAll() {
        return bookingService.findAll();
    }
}
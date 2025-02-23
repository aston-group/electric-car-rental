package ru.astongroup.carbooking.service;

import ru.astongroup.carbooking.dto.BookingReqCreateDto;
import ru.astongroup.carbooking.dto.BookingReqUpdateDto;
import ru.astongroup.carbooking.dto.BookingResponseDTO;

import java.util.Collection;

public interface BookingService {

    //1 создание брони
    BookingResponseDTO createBooking(BookingReqCreateDto bookingReqCreateDto);

    //2 редактирование брони
    BookingResponseDTO updateBooking(Long id, BookingReqUpdateDto bookingReqUpdateDto);

    //3 отмена брони
    void cancelBooking(Long id);

    //4 расчёт стоимости
    double getBookingPrice(Long id);

    //5 получить бронирования по ИД юзера
    Collection<BookingResponseDTO> getBookingsByUserId(Long userId);

    //6 подтверждение бронирования администратором
    BookingResponseDTO confirmBooking(Long bookingId);

    //7 получить все букинги, но это только надо будет для админа опцию сделать
    Collection<BookingResponseDTO> findAll();
}

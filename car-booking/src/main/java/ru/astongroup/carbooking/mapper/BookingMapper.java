package ru.astongroup.carbooking.mapper;

import ru.astongroup.carbooking.dto.BookingDto;
import ru.astongroup.carbooking.entity.Booking;

public class BookingMapper {

    public static BookingDto toBookingDto(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingDto BookingDto = new BookingDto();

        BookingDto.setId(booking.getId());
        BookingDto.setUserId(booking.getUserId());
        BookingDto.setCarId(booking.getCarId());
        BookingDto.setStartTime(booking.getStartTime());
        BookingDto.setEndTime(booking.getEndTime());
        BookingDto.setStatus(booking.getStatus());
        BookingDto.setPrice(booking.getPrice());

        return BookingDto;
    }

    public static Booking toBookingEntity(BookingDto bookingDto) {
        if (bookingDto == null) {
            return null;
        }

        Booking booking = new Booking();

        booking.setId(bookingDto.getId());
        booking.setUserId(bookingDto.getUserId());
        booking.setCarId(bookingDto.getCarId());
        booking.setStartTime(bookingDto.getStartTime());
        booking.setEndTime(bookingDto.getEndTime());
        booking.setStatus(bookingDto.getStatus());
        booking.setPrice(bookingDto.getPrice());

        return booking;
    }
}

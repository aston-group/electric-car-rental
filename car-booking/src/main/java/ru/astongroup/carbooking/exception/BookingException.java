package ru.astongroup.carbooking.exception;

public class BookingException extends RuntimeException {
    public static final String BOOKING_NOT_FOUND = "Booking not found";
    public static final String CANCELLED_BOOKING = "Cannot edit a canceled booking";

    public BookingException(String message) {
        super(message);
    }
}

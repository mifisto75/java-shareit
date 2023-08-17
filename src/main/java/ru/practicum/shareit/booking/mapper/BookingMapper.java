package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.InputBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {
    public static BookingDto toBookingDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setStart(booking.getStart());
        dto.setEnd(booking.getEnd());
        dto.setItem(booking.getItem());
        dto.setBooker(booking.getBooker());
        dto.setStatus(booking.getStatus());
        return dto;
    }

    public static Booking fromInputBookingDtoToBooking(InputBookingDto inputBookingDto, Item item, User user) {
        Booking booking = new Booking();
        booking.setId(inputBookingDto.getId());
        booking.setStart(inputBookingDto.getStart());
        booking.setEnd(inputBookingDto.getEnd());
        booking.setItem(item);
        booking.setBooker(user);
        return booking;
    }

    public static InputBookingDto toInputBookingDto(Booking booking) {
        InputBookingDto bookingDto = new InputBookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setBookerId(booking.getBooker().getId());
        bookingDto.setItemId(booking.getItem().getId());
        return bookingDto;
    }
}

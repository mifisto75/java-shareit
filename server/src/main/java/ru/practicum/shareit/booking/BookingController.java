package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.InputBookingDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private final String user = "X-Sharer-User-Id";

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDto addBooking(@RequestBody InputBookingDto inputBookingDto, @RequestHeader(user) Integer userId) { // Добавление нового запроса на бронирование.
        log.info("метод addBooking userId " + userId);
        return bookingService.addBooking(inputBookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto responseToRequest(@PathVariable Integer bookingId, @RequestHeader(user) Integer userId,
                                        @RequestParam Boolean approved) { // Подтверждение или отклонение запроса на бронирование.
        log.info("метод responseToRequest userId " + userId + "bookingId" + bookingId);
        return bookingService.responseToRequest(bookingId, userId, approved);
    }

    @GetMapping("{bookingId}")
    public BookingDto getInfoBooking(@PathVariable Integer bookingId, @RequestHeader(user) Integer userId) { // Получение данных о конкретном бронировании
        log.info("метод getInfoBooking userId " + userId + "bookingId" + bookingId);
        return bookingService.getInfoBooking(bookingId, userId);
    }

    @GetMapping
    public List<BookingDto> getAllBookingOneUser(@RequestHeader(user) Integer userId,
                                                 @RequestParam(defaultValue = "ALL") String state,
                                                 @RequestParam(defaultValue = "0", required = false) Integer from,
                                                 @RequestParam(defaultValue = "20", required = false) Integer size) { // Получение списка всех бронирований текущего пользователя.
        log.info("метод getAllBookingOneUser userId " + userId);
        return bookingService.getAllBookingOneUser(userId, state, from, size);
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllBookingOneOwner(@RequestHeader(user) Integer userId,
                                                  @RequestParam(defaultValue = "ALL") String state,
                                                  @RequestParam(defaultValue = "0", required = false) Integer from,
                                                  @RequestParam(defaultValue = "20", required = false) Integer size) { // Получение списка бронирований для всех вещей текущего пользователя.
        log.info("метод getAllBookingOneOwner userId " + userId);
        return bookingService.getAllBookingOneOwner(userId, state, from, size);

    }
}


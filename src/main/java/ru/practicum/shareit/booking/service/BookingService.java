package ru.practicum.shareit.booking.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dao.BookingDao;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.InputBookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exeptions.BadRequest;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserDao;
import ru.practicum.shareit.user.model.User;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingDao bookingDao;
    private final UserDao userDao;
    private final ItemDao itemDao;


    public BookingService(BookingDao bookingDao, UserDao userDao, ItemDao itemDao) {
        this.bookingDao = bookingDao;
        this.userDao = userDao;
        this.itemDao = itemDao;
    }

  //  @Transactional
    public BookingDto addBooking(InputBookingDto inputBookingDto, Integer userId) {
        Item item = itemDao.getItemsById(inputBookingDto.getItemId());
        if (!item.getAvailable()) {
            throw new BadRequest("предмет не доступен для аренды");
        } else if (item.getOwner().getId() == userId) {
            throw new NotFoundException("предмет не доступен для аренды");
        }
        User user = userDao.getUserById(userId);
        Booking booking = BookingMapper.fromInputBookingDtoToBooking(inputBookingDto, item, user);
        if (booking.getStart().isAfter(booking.getEnd()) || booking.getStart().equals(booking.getEnd())) {
            throw new BadRequest("ошибка в дате аренды");
        }
        return BookingMapper.toBookingDto(bookingDao.addBooking(booking));
    }

   // @Transactional
    public BookingDto responseToRequest(int bookingId, int userId, Boolean answer) {
        Booking booking = bookingDao.getBookingById(bookingId);
        if (booking.getItem().getOwner().getId() != userId) {
            throw new NotFoundException("вы не можете одобрять чужие заявки");
        } else if (!booking.getStatus().equals(BookingStatus.WAITING)) {
            throw new BadRequest("Предмет уже забронирован");
        }
        return BookingMapper.toBookingDto(bookingDao.responseToRequest(booking, answer));
    }

    // @Transactional(readOnly = true)
    public BookingDto getInfoBooking(int bookingId, int userId) {
        userDao.checkIdUserStorage(userId);
        return BookingMapper.toBookingDto(bookingDao.getInfoBooking(bookingId, userId));
    }

    public List<BookingDto> getAllBookingOneUser(int userId, String state) {
        User user = userDao.getUserById(userId);
        return bookingDao.getAllBookingOneUser(user, state)
                .stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }

    public List<BookingDto> getAllBookingOneOwner(int userId, String state) {
        User user = userDao.getUserById(userId);
        return bookingDao.getAllBookingOneOwner(user, state)
                .stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }
}

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

    public BookingDto addBooking(InputBookingDto inputBookingDto, Integer userId) {
        Item item = itemDao.getItemById(inputBookingDto.getItemId());
        if (!item.getAvailable()) {
            throw new BadRequest("предмет не доступен для аренды");
        } else if (item.getOwner().getId() == userId) {
            throw new NotFoundException("вы не можете брать в аренду свои вещи");
        }
        User user = userDao.getUserById(userId);
        Booking booking = BookingMapper.fromInputBookingDtoToBooking(inputBookingDto, item, user);
        return BookingMapper.toBookingDto(bookingDao.addBooking(booking));
    }

    public BookingDto responseToRequest(int bookingId, int userId, Boolean answer) {
        BookingDto dto = BookingMapper.toBookingDto(bookingDao.getBookingById(bookingId));
        if (dto.getItem().getOwner().getId() != userId) {
            throw new NotFoundException("вы не можете одобрять чужие заявки");
        } else if (!dto.getStatus().equals(BookingStatus.WAITING)) {
            throw new BadRequest("Предмет уже забронирован");
        }
        return BookingMapper.toBookingDto(bookingDao.responseToRequest(BookingMapper.toBooking(dto), answer));
    }

    public BookingDto getInfoBooking(int bookingId, int userId) {
        userDao.getUserById(userId);
        return BookingMapper.toBookingDto(bookingDao.getInfoBooking(bookingId, userId));
    }

    public List<BookingDto> getAllBookingOneUser(int userId, String state, int from, int size) {
        User user = userDao.getUserById(userId);
        if (from < 0) { // не смотря на валидацию from в контролере я не понимаю почему она не валидируется в букинге
            throw new BadRequest("from не может быть отрийательным ");
        }
        return bookingDao.getAllBookingOneUser(user, state, from, size)
                .stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }

    public List<BookingDto> getAllBookingOneOwner(int userId, String state, int from, int size) {
        User user = userDao.getUserById(userId);
        if (from < 0) { // не смотря на валидацию from в контролере я не понимаю почему она не валидируется в букинге
            throw new BadRequest("from не может быть отрийательным ");
        }
        return bookingDao.getAllBookingOneOwner(user, state, from, size)
                .stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }
}

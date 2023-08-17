package ru.practicum.shareit.booking.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Repository.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exeptions.BadRequest;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.exeptions.UnknownState;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookingDaoImpl implements BookingDao {
    private final BookingRepository bookingRepository;

    public BookingDaoImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    @Override
    public Booking addBooking(Booking booking) {
        booking.setStatus(BookingStatus.WAITING);
        return bookingRepository.save(booking);
    }

    @Transactional
    @Override
    public Booking responseToRequest(Booking booking, boolean answer) {
        if (answer) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    @Override
    public Booking getInfoBooking(int id, int userId) {
        Booking booking = getBookingById(id);
        if (userId != booking.getBooker().getId() && userId != booking.getItem().getOwner().getId()) {
            throw new NotFoundException("вы не можете смотреть чужие запросы");
        }
        return booking;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Booking> getAllBookingOneUser(User user, String state) {
        List<Booking> bookingList = new ArrayList<>();
        switch (state) {
            case "ALL":
                bookingList.addAll(bookingRepository.findAllByBookerOrderByStartDesc(user));
                break;
            case "CURRENT":
                bookingList.addAll(bookingRepository.findAllByBookerAndStartBeforeAndEndAfterOrderByStartDesc(
                        user, LocalDateTime.now(), LocalDateTime.now()));
                break;
            case "PAST":
                bookingList.addAll(bookingRepository.findAllByBookerAndEndBeforeOrderByStartDesc(
                        user, LocalDateTime.now()));
                break;
            case "FUTURE":
                bookingList.addAll(bookingRepository.findAllByBookerAndStartAfterOrderByStartDesc(
                        user, LocalDateTime.now()));
                break;
            case "WAITING":
                bookingList.addAll(bookingRepository.findAllByBookerAndStatusEqualsOrderByStartDesc(
                        user, BookingStatus.WAITING));
                break;
            case "REJECTED":
                bookingList.addAll(bookingRepository.findAllByBookerAndStatusEqualsOrderByStartDesc(
                        user, BookingStatus.REJECTED));
                break;
            default:
                throw new UnknownState("Неизвестный параметр " + state);
        }
        return bookingList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Booking> getAllBookingOneOwner(User user, String state) {
        List<Booking> bookingList = new ArrayList<>();
        switch (state) {
            case "ALL":
                bookingList.addAll(bookingRepository.findAllByItemOwnerOrderByStartDesc(user));
                break;
            case "CURRENT":
                bookingList.addAll(bookingRepository.findAllByItemOwnerAndStartBeforeAndEndAfterOrderByStartDesc(
                        user, LocalDateTime.now(), LocalDateTime.now()));
                break;
            case "PAST":
                bookingList.addAll(bookingRepository.findAllByItemOwnerAndEndBeforeOrderByStartDesc(
                        user, LocalDateTime.now()));
                break;
            case "FUTURE":
                bookingList.addAll(bookingRepository.findAllByItemOwnerAndStartAfterOrderByStartDesc(
                        user, LocalDateTime.now()));
                break;
            case "WAITING":
                bookingList.addAll(bookingRepository.findAllByItemOwnerAndStatusEqualsOrderByStartDesc(
                        user, BookingStatus.WAITING));
                break;
            case "REJECTED":
                bookingList.addAll(bookingRepository.findAllByItemOwnerAndStatusEqualsOrderByStartDesc(
                        user, BookingStatus.REJECTED));
                break;
            default:
                throw new UnknownState("Неизвестный параметр " + state);
        }
        return bookingList;
    }

    @Transactional(readOnly = true)
    @Override
    public Booking getBookingById(Integer id) {
        return bookingRepository.findById(id).orElseThrow(() -> new NotFoundException("такова запроса нет"));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Booking> getLast(int id) {
        return bookingRepository.findFirstByItemIdAndStatusAndStartBeforeOrderByStartDesc
                (id, BookingStatus.APPROVED, LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Booking> getNext(int id) {
        return bookingRepository.findFirstByItemIdAndStatusAndStartAfterOrderByStartAsc
                (id, BookingStatus.APPROVED, LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    @Override
    public void checkUserBooking(Integer userId, Integer itemId) {
        if (!bookingRepository.existsByBookerIdAndItemIdAndEndIsBefore(userId, itemId, LocalDateTime.now())) {
            throw new BadRequest("вы не можете оставлять комментарии на вещь которой не полизывались");
        }
    }

}

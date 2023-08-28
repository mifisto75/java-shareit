package ru.practicum.shareit.bookingTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.Repository.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingRepositoryTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookingRepository bookingRepository;

    PageRequest page = PageRequest.of(0, 2);


    @Test
    void findAllByBookerOrderByStartDesc() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("test1");
        user1.setEmail("tset@email.com1");
        userRepository.save(user1);

        Item item1 = new Item();
        item1.setId(1);
        item1.setName("test1");
        item1.setDescription("test1");
        item1.setAvailable(true);
        item1.setOwner(user1);
        itemRepository.save(item1);

        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2023, 11, 11, 11, 11, 1));
        booking.setEnd(LocalDateTime.of(2023, 11, 11, 11, 11, 2));
        booking.setItem(item1);
        booking.setBooker(user1);
        booking.setStatus(BookingStatus.WAITING);
        bookingRepository.save(booking);

        List<Booking> list = bookingRepository.findAllByBookerOrderByStartDesc(user1, page).toList();
        Assertions.assertEquals(list.get(0), booking);
    }

    @Test
    void findAllByBookerAndStartBeforeAndEndAfterOrderByStartDesc() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("test1");
        user1.setEmail("tset@email.com1");
        userRepository.save(user1);

        Item item1 = new Item();
        item1.setId(1);
        item1.setName("test1");
        item1.setDescription("test1");
        item1.setAvailable(true);
        item1.setOwner(user1);
        itemRepository.save(item1);

        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.now());
        booking.setEnd(LocalDateTime.of(2023, 11, 11, 11, 11, 2));
        booking.setItem(item1);
        booking.setBooker(user1);
        booking.setStatus(BookingStatus.WAITING);
        bookingRepository.save(booking);

        List<Booking> list = bookingRepository
                .findAllByBookerAndStartBeforeAndEndAfterOrderByStartDesc(
                        user1, LocalDateTime.now(), LocalDateTime.now(), page).toList();
        Assertions.assertEquals(list.get(0), booking);
    }

    @Test
    void findAllByBookerAndEndBeforeOrderByStartDesc() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("test1");
        user1.setEmail("tset@email.com1");
        userRepository.save(user1);

        Item item1 = new Item();
        item1.setId(1);
        item1.setName("test1");
        item1.setDescription("test1");
        item1.setAvailable(true);
        item1.setOwner(user1);
        itemRepository.save(item1);

        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2022, 11, 11, 11, 11, 2));
        booking.setEnd(LocalDateTime.now());
        booking.setItem(item1);
        booking.setBooker(user1);
        booking.setStatus(BookingStatus.WAITING);
        bookingRepository.save(booking);

        List<Booking> list = bookingRepository
                .findAllByBookerAndEndBeforeOrderByStartDesc(
                        user1, LocalDateTime.now(), page).toList();
        Assertions.assertEquals(list.get(0), booking);
    }


    @Test
    void findAllByBookerAndStartAfterOrderByStartDesc() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("test1");
        user1.setEmail("tset@email.com1");
        userRepository.save(user1);

        Item item1 = new Item();
        item1.setId(1);
        item1.setName("test1");
        item1.setDescription("test1");
        item1.setAvailable(true);
        item1.setOwner(user1);
        itemRepository.save(item1);

        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2023, 11, 11, 11, 11, 1));
        booking.setEnd(LocalDateTime.of(2023, 11, 11, 11, 11, 2));
        booking.setItem(item1);
        booking.setBooker(user1);
        booking.setStatus(BookingStatus.WAITING);
        bookingRepository.save(booking);

        List<Booking> list = bookingRepository.findAllByBookerAndStartAfterOrderByStartDesc(
                user1, LocalDateTime.now(), page).toList();
        Assertions.assertEquals(list.get(0), booking);
    }

    @Test
    void findAllByBookerAndStatusEqualsOrderByStartDesc() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("test1");
        user1.setEmail("tset@email.com1");
        userRepository.save(user1);

        Item item1 = new Item();
        item1.setId(1);
        item1.setName("test1");
        item1.setDescription("test1");
        item1.setAvailable(true);
        item1.setOwner(user1);
        itemRepository.save(item1);

        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2023, 11, 11, 11, 11, 1));
        booking.setEnd(LocalDateTime.of(2023, 11, 11, 11, 11, 2));
        booking.setItem(item1);
        booking.setBooker(user1);
        booking.setStatus(BookingStatus.WAITING);
        bookingRepository.save(booking);

        List<Booking> list = bookingRepository.findAllByBookerAndStatusEqualsOrderByStartDesc(
                user1, BookingStatus.WAITING, page).toList();
        Assertions.assertEquals(list.get(0), booking);
    }

    @Test
    void findAllByItemOwnerOrderByStartDesc() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("test1");
        user1.setEmail("tset@email.com1");
        userRepository.save(user1);

        Item item1 = new Item();
        item1.setId(1);
        item1.setName("test1");
        item1.setDescription("test1");
        item1.setAvailable(true);
        item1.setOwner(user1);
        itemRepository.save(item1);

        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2023, 11, 11, 11, 11, 1));
        booking.setEnd(LocalDateTime.of(2023, 11, 11, 11, 11, 2));
        booking.setItem(item1);
        booking.setBooker(user1);
        booking.setStatus(BookingStatus.WAITING);
        bookingRepository.save(booking);

        List<Booking> list = bookingRepository.findAllByItemOwnerOrderByStartDesc(user1, page).toList();
        Assertions.assertEquals(list.get(0), booking);
    }

    @Test
    void findAllByItemOwnerAndStartBeforeAndEndAfterOrderByStartDesc() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("test1");
        user1.setEmail("tset@email.com1");
        userRepository.save(user1);

        Item item1 = new Item();
        item1.setId(1);
        item1.setName("test1");
        item1.setDescription("test1");
        item1.setAvailable(true);
        item1.setOwner(user1);
        itemRepository.save(item1);

        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.now());
        booking.setEnd(LocalDateTime.of(2023, 11, 11, 11, 11, 2));
        booking.setItem(item1);
        booking.setBooker(user1);
        booking.setStatus(BookingStatus.WAITING);
        bookingRepository.save(booking);

        List<Booking> list = bookingRepository
                .findAllByItemOwnerAndStartBeforeAndEndAfterOrderByStartDesc(
                        user1, LocalDateTime.now(), LocalDateTime.now(), page).toList();
        Assertions.assertEquals(list.get(0), booking);
    }

    @Test
    void findAllByItemOwnerAndEndBeforeOrderByStartDesc() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("test1");
        user1.setEmail("tset@email.com1");
        userRepository.save(user1);

        Item item1 = new Item();
        item1.setId(1);
        item1.setName("test1");
        item1.setDescription("test1");
        item1.setAvailable(true);
        item1.setOwner(user1);
        itemRepository.save(item1);

        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2022, 11, 11, 11, 11, 2));
        booking.setEnd(LocalDateTime.now());
        booking.setItem(item1);
        booking.setBooker(user1);
        booking.setStatus(BookingStatus.WAITING);
        bookingRepository.save(booking);

        List<Booking> list = bookingRepository
                .findAllByItemOwnerAndEndBeforeOrderByStartDesc(
                        user1, LocalDateTime.now(), page).toList();
        Assertions.assertEquals(list.get(0), booking);
    }

    @Test
    void findAllByItemOwnerAndStartAfterOrderByStartDesc() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("test1");
        user1.setEmail("tset@email.com1");
        userRepository.save(user1);

        Item item1 = new Item();
        item1.setId(1);
        item1.setName("test1");
        item1.setDescription("test1");
        item1.setAvailable(true);
        item1.setOwner(user1);
        itemRepository.save(item1);

        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2023, 11, 11, 11, 11, 1));
        booking.setEnd(LocalDateTime.of(2023, 11, 11, 11, 11, 2));
        booking.setItem(item1);
        booking.setBooker(user1);
        booking.setStatus(BookingStatus.WAITING);
        bookingRepository.save(booking);

        List<Booking> list = bookingRepository.findAllByItemOwnerAndStartAfterOrderByStartDesc(
                user1, LocalDateTime.now(), page).toList();
        Assertions.assertEquals(list.get(0), booking);
    }

    @Test
    void findAllByItemOwnerAndStatusEqualsOrderByStartDesc() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("test1");
        user1.setEmail("tset@email.com1");
        userRepository.save(user1);

        Item item1 = new Item();
        item1.setId(1);
        item1.setName("test1");
        item1.setDescription("test1");
        item1.setAvailable(true);
        item1.setOwner(user1);
        itemRepository.save(item1);

        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2023, 11, 11, 11, 11, 1));
        booking.setEnd(LocalDateTime.of(2023, 11, 11, 11, 11, 2));
        booking.setItem(item1);
        booking.setBooker(user1);
        booking.setStatus(BookingStatus.WAITING);
        bookingRepository.save(booking);

        List<Booking> list = bookingRepository.findAllByItemOwnerAndStatusEqualsOrderByStartDesc(
                user1, BookingStatus.WAITING, page).toList();
        Assertions.assertEquals(list.get(0), booking);
    }

}

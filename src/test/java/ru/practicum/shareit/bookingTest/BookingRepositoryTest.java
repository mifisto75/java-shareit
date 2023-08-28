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
import java.util.Optional;

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


    @Test
    void findFirstByItemIdAndStatusAndStartBeforeOrderByStartDesc() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("test1");
        user1.setEmail("test1@test.com");
        userRepository.save(user1);

        Item item1 = new Item();
        item1.setId(1);
        item1.setName("item1");
        item1.setDescription("description1");
        item1.setAvailable(true);
        item1.setOwner(user1);
        itemRepository.save(item1);

        Booking booking1 = new Booking();
        booking1.setId(1);
        booking1.setStart(LocalDateTime.now());
        booking1.setEnd(LocalDateTime.of(2023, 11, 11, 11, 11, 2));
        booking1.setItem(item1);
        booking1.setBooker(user1);
        booking1.setStatus(BookingStatus.APPROVED);
        bookingRepository.save(booking1);

        Booking booking2 = new Booking();
        booking2.setId(1);
        booking2.setStart(LocalDateTime.now());
        booking2.setEnd(LocalDateTime.of(2023, 11, 12, 11, 11, 2));
        booking2.setItem(item1);
        booking2.setBooker(user1);
        booking2.setStatus(BookingStatus.APPROVED);
        bookingRepository.save(booking2);


        Optional<Booking> last = bookingRepository.findFirstByItemIdAndStatusAndStartBeforeOrderByStartDesc(
                item1.getId(), BookingStatus.APPROVED, LocalDateTime.now());
        Assertions.assertEquals(last.get(), booking2);
    }

    @Test
    void findFirstByItemIdAndStatusAndStartAfterOrderByStartAsc() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("test1");
        user1.setEmail("test1@test.com");
        userRepository.save(user1);

        Item item1 = new Item();
        item1.setId(1);
        item1.setName("item1");
        item1.setDescription("description1");
        item1.setAvailable(true);
        item1.setOwner(user1);
        itemRepository.save(item1);

        Booking booking1 = new Booking();
        booking1.setId(1);
        booking1.setStart(LocalDateTime.now());
        booking1.setEnd(LocalDateTime.of(2023, 11, 11, 11, 11, 2));
        booking1.setItem(item1);
        booking1.setBooker(user1);
        booking1.setStatus(BookingStatus.APPROVED);
        bookingRepository.save(booking1);

        Booking booking2 = new Booking();
        booking2.setId(1);
        booking2.setStart(LocalDateTime.of(2023, 11, 11, 11, 11, 3));
        booking2.setEnd(LocalDateTime.of(2023, 11, 12, 11, 11, 2));
        booking2.setItem(item1);
        booking2.setBooker(user1);
        booking2.setStatus(BookingStatus.APPROVED);
        bookingRepository.save(booking2);


        Optional<Booking> last = bookingRepository.findFirstByItemIdAndStatusAndStartAfterOrderByStartAsc(
                item1.getId(), BookingStatus.APPROVED, LocalDateTime.now());
        Assertions.assertEquals(last.get(), booking2);
    }

    @Test
    void existsByBookerIdAndItemIdAndEndIsBefore() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("test1");
        user1.setEmail("test1@test.com");
        userRepository.save(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setName("test2");
        user2.setEmail("test2@test.com");
        userRepository.save(user2);

        Item item1 = new Item();
        item1.setId(1);
        item1.setName("item1");
        item1.setDescription("description1");
        item1.setAvailable(true);
        item1.setOwner(user1);
        itemRepository.save(item1);

        Booking booking1 = new Booking();
        booking1.setId(1);
        booking1.setStart(LocalDateTime.of(2023, 11, 11, 11, 11, 1));
        booking1.setEnd(LocalDateTime.of(2023, 11, 11, 11, 11, 2));
        booking1.setItem(item1);
        booking1.setBooker(user1);
        booking1.setStatus(BookingStatus.WAITING);
        bookingRepository.save(booking1);

        Booking booking2 = new Booking();
        booking2.setId(2);
        booking2.setStart(LocalDateTime.of(2023, 11, 12, 11, 11, 1));
        booking2.setEnd(LocalDateTime.of(2023, 11, 12, 11, 11, 2));
        booking2.setItem(item1);
        booking2.setBooker(user2);
        booking2.setStatus(BookingStatus.WAITING);
        bookingRepository.save(booking2);

        boolean result = bookingRepository.existsByBookerIdAndItemIdAndEndIsBefore(
                user1.getId(), item1.getId(), LocalDateTime.now());
        Assertions.assertEquals(result, false);
    }
}

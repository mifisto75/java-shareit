package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dao.BookingDao;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserDao;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {
    public final ItemDao itemDao;
    private final UserDao userDao;
    private final BookingDao bookingDao;


    public ItemService(ItemDao itemDao, UserDao userDao, BookingDao bookingDao) {
        this.itemDao = itemDao;
        this.userDao = userDao;
        this.bookingDao = bookingDao;

    }

 //   @Transactional
    public ItemDto addItems(ItemDto itemDto, int ownerId) {
        userDao.checkIdUserStorage(ownerId);
        Item item = ItemMapper.toItem(itemDto, userDao.getUserById(ownerId));
        return ItemMapper.toItemDto(itemDao.addItems(item));
    }

  //  @Transactional
    public ItemDto updateItems(int itemId, ItemDto itemDto, int ownerId) {
        Item item = ItemMapper.toItem(itemDto, userDao.getUserById(ownerId));
        return ItemMapper.toItemDto(itemDao.updateItems(itemId, item));
    }

 //   @Transactional(readOnly = true)
    public ItemDto getItemsById(int itemId, int ownerId) {
        Item item = itemDao.getItemsById(itemId);
        ItemDto dto = ItemMapper.toItemDto(item);
        setDtoComment(dto);
        if (ownerId == item.getOwner().getId()) {
            setDtoNextAndLast(dto);
        }
        return dto;
    }


  //  @Transactional(readOnly = true)
    public List<ItemDto> getAllItemsOneUser(int ownerId) {
        return itemDao.getAllItemsOneUser(ownerId)
                .stream()
                .map(ItemMapper::toItemDto)
                .map(this::setDtoComment)
                .map(this::setDtoNextAndLast)
                .collect(Collectors.toList());
    }

  //  @Transactional(readOnly = true)
    public List<ItemDto> searchItemByText(String text) {
        if (text.isBlank()) {
            return new ArrayList<ItemDto>();
        }
        return itemDao.searchItemByText(text)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

   // @Transactional
    public CommentDto addComment(int itemId, int userId, CommentDto commentDto) {
        bookingDao.checkUserBooking(userId, itemId);
        Item item = itemDao.getItemsById(itemId);
        User user = userDao.getUserById(userId);
        Comment comment = CommentMapper.toComment(commentDto, user, item);
        return CommentMapper.toCommentDto(itemDao.addComment(comment));

    }

    public ItemDto setDtoComment(ItemDto dto) {
        dto.setComments(itemDao.getAllCommentOneItem(dto.getId())
                .stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList()));
        return dto;
    }

 //   @Transactional
    public ItemDto setDtoNextAndLast(ItemDto dto) {
        Optional<Booking> lastBooking = bookingDao.getLast(dto.getId());
        Optional<Booking> nextBooking = bookingDao.getNext(dto.getId());
        if (lastBooking.isPresent()) {
            dto.setLastBooking(BookingMapper.toInputBookingDto(lastBooking.get()));
        } else {
            dto.setLastBooking(null);
        }

        if (nextBooking.isPresent()) {
            dto.setNextBooking(BookingMapper.toInputBookingDto(nextBooking.get()));
        } else {
            dto.setNextBooking(null);
        }
        return dto;
    }
}

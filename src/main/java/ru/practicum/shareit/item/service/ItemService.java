package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    public final ItemDao itemDao;
    private final UserDao userDao;

    public ItemService(ItemDao itemDao, UserDao userDao) {
        this.itemDao = itemDao;
        this.userDao = userDao;
    }

    public ItemDto addItems(ItemDto itemDto, int ownerId) {
        userDao.checkIdUserStorage(ownerId);
        Item item = ItemMapper.toItem(itemDto, ownerId);
        return ItemMapper.toItemDto(itemDao.addItems(item));
    }

    public ItemDto updateItems(int itemId, ItemDto itemDto, int ownerId) {
        Item item = ItemMapper.toItem(itemDto, ownerId);
        return ItemMapper.toItemDto(itemDao.updateItems(itemId, item));
    }

    public ItemDto getItemsById(int itemId) {
        return ItemMapper.toItemDto(itemDao.getItemsById(itemId));
    }

    public List<ItemDto> getAllItemsOneUser(int ownerId) {
        return itemDao.getAllItemsOneUser(ownerId)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public List<ItemDto> searchItemByText(String text) {
        if (text.isBlank()) {
            return new ArrayList<ItemDto>();
        }
        return itemDao.searchItemByText(text)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}

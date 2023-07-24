package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    public final ItemDao itemDao;
    private final UserDao userDao;

    public ItemService(ItemDao itemDao, UserDao userDao) {
        this.itemDao = itemDao;
        this.userDao = userDao;
    }

    public ItemDto addItems(Item item, int ownerId) {
        userDao.checkIdUserStorage(ownerId);
        return itemDao.addItems(item, ownerId);
    }

    public List<ItemDto> searchItemByText(String text) {
        if (text.isBlank()) {
            return new ArrayList<ItemDto>();
        }
        return itemDao.searchItemByText(text);
    }
}

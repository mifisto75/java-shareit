package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemDao {

    ItemDto addItems(Item item, int ownerId);    // addItems Добавление новой вещи


    ItemDto updateItems(int itemId, Item item, int ownerId); //updateItems Редактирование вещи


    ItemDto getItemsById(int itemId); // getItemsById Просмотр информации о конкретной вещи по её идентификатору


    List<ItemDto> getAllItemsOneUser(int ownerId);// getAllItemsOneUser Просмотр владельцем списка всех его вещей


    List<ItemDto> searchItemByText(String text);// Поиск вещи потенциальным арендатором

    void checkIdItemStorage(int id);
}

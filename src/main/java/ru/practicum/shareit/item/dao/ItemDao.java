package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemDao {

    Item addItems(Item item);    // addItems Добавление новой вещи


    Item updateItems(int itemId, Item item); //updateItems Редактирование вещи


    Item getItemsById(int itemId); // getItemsById Просмотр информации о конкретной вещи по её идентификатору


    List<Item> getAllItemsOneUser(int ownerId);// getAllItemsOneUser Просмотр владельцем списка всех его вещей


    List<Item> searchItemByText(String text);// Поиск вещи потенциальным арендатором

    Comment addComment(Comment comment);

    List<Comment> getAllCommentOneItem(int id);
}

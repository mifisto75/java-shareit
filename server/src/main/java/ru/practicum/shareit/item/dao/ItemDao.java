package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemDao {

    Item addItem(Item item);    // Добавление новой вещи


    Item updateItem(int itemId, Item item); //Редактирование вещи


    Item getItemById(int itemId); //Просмотр информации о конкретной вещи по её идентификатору


    List<Item> getAllItemsOneUser(int ownerId, int from, int size);// Просмотр владельцем списка всех его вещей


    List<Item> searchItemByText(String text, int from, int size);// Поиск вещи потенциальным арендатором

    Comment addComment(Comment comment);

    List<Comment> getAllCommentOneItem(int id);

    List<Item> getAllItemsByOneRequest(int requestId);
}

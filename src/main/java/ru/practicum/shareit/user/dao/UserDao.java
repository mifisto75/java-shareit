package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserDao {
    //CRUD операции
    User addUser(User user); //создание пользывателя

    User updateUser(User user, int id); // обновление пользывателя

    User getUserById(int id); // выдача пользывателя по ID

    List<User> getAllUser(); // выдача всех пользывателей

    void deleteUser(int id); // удаление пользывателя

}

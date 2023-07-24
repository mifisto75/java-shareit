package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserDao {
    //CRUD операции
    UserDto addUser(User user); //создание пользывателя

    UserDto updateUser(User user, int id); // обновление пользывателя

    UserDto getUserById(int id); // выдача пользывателя по ID

    List<UserDto> getAllUser(); // выдача всех пользывателей

    void deleteUser(int id); // удаление пользывателя

    void checkIdUserStorage(int id);

    void checkEmailUserStorage(User user);
}

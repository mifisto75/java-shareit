package ru.practicum.shareit.user.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dao.UserDao;

@Service
public class UserService {
    public final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
}

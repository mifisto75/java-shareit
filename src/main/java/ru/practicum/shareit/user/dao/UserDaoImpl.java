package ru.practicum.shareit.user.dao;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exeptions.Conflict;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDaoImpl implements UserDao {
    private int nextUserId = 1;
    public HashMap<Integer, User> userStorage = new HashMap<>(); //хранение юзеров в памяти

    @Override
    public UserDto addUser(User user) {
        checkEmailUserStorage(user);
        user.setId(nextUserId++);
        userStorage.put(user.getId(), user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(User user, int id) {
        checkIdUserStorage(id);
        if (user.getName() != null) {
            userStorage.get(id).setName(user.getName());
        }
        if (user.getEmail() != null) {
            if (!user.getEmail().equals(userStorage.get(id).getEmail())) {
                checkEmailUserStorage(user);
                userStorage.get(id).setEmail(user.getEmail());
            }
        }
        return UserMapper.toUserDto(userStorage.get(id));
    }

    @Override
    public UserDto getUserById(int id) {
        checkIdUserStorage(id);
        return UserMapper.toUserDto(userStorage.get(id));
    }

    @Override
    public List<UserDto> getAllUser() {
        return userStorage.values().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }


    @Override
    public void deleteUser(int id) {
        checkIdUserStorage(id);
        userStorage.remove(id);
    }

    @Override
    public void checkIdUserStorage(int id) {
        if (!userStorage.containsKey(id)) {
            throw new NotFoundException("по вашему id не был найден пользыатель");
        }
    }

    @Override
    public void checkEmailUserStorage(User user) {
        for (User baza : userStorage.values()) {
            if (baza.getEmail().equals(user.getEmail())) {
                throw new Conflict("Эта почта уже занята ");
            }
        }
    }
}

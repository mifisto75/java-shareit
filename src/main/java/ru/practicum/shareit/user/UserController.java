package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public UserDto addUser(@Valid @RequestBody User user) { //создание пользывателя
        return userService.userDao.addUser(user);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@RequestBody User user, @PathVariable Integer userId) { // обновление пользывателя
        return userService.userDao.updateUser(user, userId);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Integer userId) { // выдача пользывателя по ID
        return userService.userDao.getUserById(userId);
    }

    @GetMapping
    public List<UserDto> getAllUser() { // выдача всех пользывателей
        return userService.userDao.getAllUser();
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) { // удаление пользывателя
        userService.userDao.deleteUser(userId);
    }

}

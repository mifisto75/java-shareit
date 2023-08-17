package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
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
    public UserDto addUser(@Valid @RequestBody UserDto userDto) { //создание пользывателя
        log.info("метод addUser");
        return userService.addUser(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@RequestBody UserDto userDto, @PathVariable Integer userId) { // обновление пользывателя
        log.info("метод updateUser userId " + userId);
        return userService.updateUser(userDto, userId);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Integer userId) { // выдача пользывателя по ID
        log.info("метод getUserById userId " + userId);
        return userService.getUserById(userId);
    }

    @GetMapping
    public List<UserDto> getAllUser() { // выдача всех пользывателей
        log.info("метод getUserById");
        return userService.getAllUser();
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) { // удаление пользывателя
        log.info("метод deleteUser userId " + userId);
        userService.deleteUser(userId);
    }

}

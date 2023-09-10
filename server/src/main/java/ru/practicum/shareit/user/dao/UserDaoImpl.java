package ru.practicum.shareit.user.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;


@Component
public class UserDaoImpl implements UserDao {
    private final UserRepository userRepository;

    public UserDaoImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(User user, int id) {
        User originalUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("по вашему id не был найден пользователь"));
        Optional.ofNullable(user.getEmail()).ifPresent(originalUser::setEmail);
        Optional.ofNullable(user.getName()).ifPresent(originalUser::setName);
        return userRepository.save(originalUser);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("по вашему id не был найден пользователь"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

}

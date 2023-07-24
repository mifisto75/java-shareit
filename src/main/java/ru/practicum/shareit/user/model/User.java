package ru.practicum.shareit.user.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
public class User {
    private int id;
    @NotBlank
    private String name;
    @NotBlank(message = "почта не может быть пустой")
    @Email(message = "не корректно указанная почта")
    private String email;

}

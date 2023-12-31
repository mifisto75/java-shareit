package ru.practicum.shareit.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data

public class UserDto {
    @NotBlank(message = "not NULL")
    private String name;
    @NotBlank(message = "почта не может быть пустой")
    @Email(message = "не корректно указанная почта")
    private String email;
}

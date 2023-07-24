package ru.practicum.shareit.item.model;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Item {
    private int id; // — уникальный идентификатор вещи;
    @NotBlank
    private String name; //  — краткое название;
    @NotBlank
    private String description; // — развёрнутое описание;
    @NotNull
    private Boolean available; // — статус о том, доступна или нет вещь для аренды;
    private Integer owner; // — владелец вещи;
    private Integer request; //  — если вещь была создана по запросу другого пользователя, то в этом
    //поле будет храниться ссылка на соответствующий запрос.

}

package ru.practicum.shareit.item.model;


import lombok.Data;

@Data
public class Item {
    private int id; // — уникальный идентификатор вещи;

    private String name; //  — краткое название;

    private String description; // — развёрнутое описание;

    private Boolean available; // — статус о том, доступна или нет вещь для аренды;
    private Integer owner; // — владелец вещи;
    private Integer request; //  — если вещь была создана по запросу другого пользователя, то в этом
    //поле будет храниться ссылка на соответствующий запрос.

}

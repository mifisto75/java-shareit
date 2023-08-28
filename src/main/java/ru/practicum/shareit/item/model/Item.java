package ru.practicum.shareit.item.model;


import lombok.Data;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;

@Entity
@Data
@Table(name = "items")
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // — уникальный идентификатор вещи;
    @Column(length = 200, nullable = false)
    private String name; //  — краткое название;
    @Column(length = 1000, nullable = false)
    private String description; // — развёрнутое описание;
    @Column
    private Boolean available; // — статус о том, доступна или нет вещь для аренды;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner; // — владелец вещи;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private ItemRequest request; //  — если вещь была создана по запросу другого пользователя, то в этом
    //поле будет храниться ссылка на соответствующий запрос. (оставим для слежущах фз)

}

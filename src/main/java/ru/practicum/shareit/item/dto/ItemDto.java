package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.booking.dto.InputBookingDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ItemDto {
    private int id; // — уникальный идентификатор вещи;
    @NotBlank
    private String name; //  — краткое название;
    @NotBlank
    private String description; // — развёрнутое описание;
    @NotNull
    private Boolean available; // — статус о том, доступна или нет вещь для аренды;

    private List<CommentDto> comments;

    private InputBookingDto lastBooking;

    private InputBookingDto nextBooking;
    //private Integer request; //  — если вещь была создана по запросу другого пользователя, то в этом
    //поле будет храниться ссылка на соответствующий запрос.

}

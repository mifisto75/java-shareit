package ru.practicum.shareit.booking.dto;


import lombok.Data;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class BookingDto {

    private int id;

    @NotNull(message = "not NULL")
    @FutureOrPresent
    private LocalDateTime start;

    @NotNull(message = "not NULL")
    @Future
    private LocalDateTime end;

    private User booker;

    private Item item;

    private BookingStatus status = BookingStatus.WAITING;


}

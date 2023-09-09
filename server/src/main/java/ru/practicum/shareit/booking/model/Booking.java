package ru.practicum.shareit.booking.model;


import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private int id; // уникальный идентификатор бронирования;
    @Column(name = "start_time", nullable = false)
    private LocalDateTime start; // дата и время начала бронирования
    @Column(name = "end_time", nullable = false)
    private LocalDateTime end; //  дата и время конца бронирования
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item; // вещь, которую пользователь бронирует
    @ManyToOne
    @JoinColumn(name = "booker_id")
    private User booker; // пользователь, который осуществляет бронирование
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private BookingStatus status; // статус бронирования


}

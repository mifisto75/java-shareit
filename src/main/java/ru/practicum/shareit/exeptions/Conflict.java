package ru.practicum.shareit.exeptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Conflict extends IllegalArgumentException {
    public Conflict(String message) {
        super(message);
        log.warn(message);
    }
}

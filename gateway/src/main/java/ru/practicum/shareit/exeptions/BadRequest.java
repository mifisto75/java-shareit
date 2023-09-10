package ru.practicum.shareit.exeptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BadRequest extends IllegalArgumentException {
    public BadRequest(String message) {
        super(message);
        log.warn(message);
    }
}

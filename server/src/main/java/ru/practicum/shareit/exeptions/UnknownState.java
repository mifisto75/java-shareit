package ru.practicum.shareit.exeptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnknownState extends IllegalArgumentException {
    public UnknownState(String message) {
        super(message);
        log.warn(message);
    }
}

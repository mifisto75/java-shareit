package ru.practicum.shareit.exeptions.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exeptions.BadRequest;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.exeptions.model.ErrorResponse;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerNotFoundException(final NotFoundException e) {
        return new ErrorResponse("NOT FOUND", e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerBadRequest(final BadRequest e) {
        return new ErrorResponse("BAD REQUEST", e.getMessage());
    }

}

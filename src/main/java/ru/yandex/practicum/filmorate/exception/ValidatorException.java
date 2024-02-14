package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidatorException extends IllegalArgumentException{
    public ValidatorException(final String message) {
        log.error(message);
    }
}

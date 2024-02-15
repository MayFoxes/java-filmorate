package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidatorException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private int id;
    private final HashMap<Integer, Film> films = new HashMap<>();

    private int idGenerated() {
        return id++;
    }

    @GetMapping(value = "/films")
    public List<Film> getAllFilm() {
        return new ArrayList<>(films.values());
    }

    @PostMapping(value = "/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        film.setId(idGenerated());
        films.put(film.getId(), film);
        log.info("Film added: {}", film);
        return film;
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Film updated: {}", film);
            return film;
        }
        throw new ValidatorException("Wrong id / do not have this film:" + film);
    }
}


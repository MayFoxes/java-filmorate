package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping(value = "/films")
    public Film update(@Valid @RequestBody Film film) {
        return filmService.update(film);
    }

    @PutMapping(value = "/films/{id}/like/{userId}")
    public Film likeFilm(
            @PathVariable Integer id,
            @PathVariable Integer userId
    ) {
        return filmService.likeFilm(id, userId);
    }

    @DeleteMapping(value = "/films/{id}/like/{userId}")
    public Film deleteLike(
            @PathVariable Integer id,
            @PathVariable Integer userId
    ) {
        return filmService.dislikeFilm(id, userId);
    }

    @GetMapping(value = "/films")
    public List<Film> findAll() {
        return filmService.findAll();
    }

    @GetMapping(value = "/films/{id}")
    public Film findById(@PathVariable Integer id) {
        return filmService.findById(id);
    }

    @GetMapping(value = "/films/popular")
    public List<Film> getPopular(
            @RequestParam(defaultValue = "10", required = false) @PositiveOrZero Integer count
    ) {
        return filmService.getPopular(count);
    }
}


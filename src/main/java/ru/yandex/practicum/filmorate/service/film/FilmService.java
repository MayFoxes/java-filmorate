package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    List<Film> findAll();

    Film findById(Integer id);

    Film create(Film film);

    Film update(Film film);

    Film likeFilm(Integer id, Integer userId);

    Film dislikeFilm(Integer id, Integer userId);

    List<Film> getPopular(Integer count);
}

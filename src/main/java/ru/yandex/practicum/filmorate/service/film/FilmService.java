package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    List<Film> findAll();

    Film findById(int id);

    Film create(Film film);

    Film update(Film film);

    Film likeFilm(int id, int userId);

    Film dislikeFilm(int id, int userId);

    List<Film> getPopular(int count);
}

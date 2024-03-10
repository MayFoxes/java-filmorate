package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film create(Film film);

    Film update(Film film);

    Film findById(Integer filmId);

    List<Film> findAll();

    Film likeFilm(Integer id, Integer userId);

    Film deleteLike(Integer id, Integer userId);

    List<Film> getPopular(Integer count);

    void checkFilmExist(Integer id);
}

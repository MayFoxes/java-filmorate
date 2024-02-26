package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film create(Film film);

    Film update(Film film);

    Film findById(int filmId);

    List<Film> findAll();

    Film likeFilm(int id, int userId);

    Film deleteLike(int id, int userId);

    List<Film> getPopular(int count);

    void checkFilmExist(int id);
}

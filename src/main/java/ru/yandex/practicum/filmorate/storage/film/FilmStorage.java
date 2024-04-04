package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Film addFilm(Film film);

    void deleteFilm(Integer id);

    Film updateFilm(Film film);

    Film getFilmById(Integer filmId);

    Collection<Film> getAllFilms();

    void addLike(Integer id, Integer userId);

    void deleteLike(Integer id, Integer userId);

    Collection<Film> getPopular(Integer count);

    void checkFilmExist(Integer id);

    Collection<Film> getUserRecommendations(Integer userId);
}

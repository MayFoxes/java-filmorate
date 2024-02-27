package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private int id = 0;
    private final HashMap<Integer, Film> films = new HashMap<>();

    private int idGenerated() {
        return ++id;
    }

    @Override
    public Film create(Film film) {
        film.setId(idGenerated());
        films.put(film.getId(), film);
        log.info("Film added: {}", film);

        return film;
    }

    @Override
    public Film update(Film film) {
        checkFilmExist(film.getId());
        films.put(film.getId(), film);
        log.info("Film updated: {}", film);

        return film;
    }

    @Override
    public Film findById(Integer filmId) {
        checkFilmExist(filmId);

        return films.get(filmId);
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film likeFilm(Integer id, Integer userId) {
        Film film = saveLike(id, userId);

        film.setRate(film.getRate() + 1);

        return films.get(id);
    }

    @Override
    public Film deleteLike(Integer id, Integer userId) {
        Film film = removeLike(id, userId);

        film.setRate(film.getRate() - 1);

        return films.get(id);
    }

    @Override
    public List<Film> getPopular(Integer count) {
        return films.values()
                .stream()
                .sorted(Comparator.comparing(Film::getRate).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public void checkFilmExist(Integer id) {
        if (films.get(id) == null) {
            throw new FilmNotFoundException(String.format("There are no such a film with id=%d: %s", id, films.get(id)));
        }
    }

    private Film saveLike(Integer id, Integer userId) {
        Film film = films.get(id);
        Set<Integer> likes = film.getLikes();
        likes.add(userId);
        film.setLikes(likes);

        return update(film);
    }

    private Film removeLike(Integer id, Integer userId) {
        Film film = films.get(id);
        Set<Integer> likes = film.getLikes();
        likes.remove(userId);
        film.setLikes(likes);

        return update(film);
    }
}

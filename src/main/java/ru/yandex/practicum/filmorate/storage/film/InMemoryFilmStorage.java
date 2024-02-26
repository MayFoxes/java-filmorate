package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private int id;
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
    public Film findById(int filmId) {
        checkFilmExist(filmId);

        return films.get(filmId);
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film likeFilm(int id, int userId) {
        Film film = films.get(id);

        film.getLikes().add(userId);
        film.setRate(film.getRate() + 1);

        return films.get(id);
    }

    @Override
    public Film deleteLike(int id, int userId) {
        Film film = films.get(id);

        film.getLikes().remove(userId);
        film.setRate(film.getRate() - 1);

        return films.get(id);
    }

    @Override
    public List<Film> getPopular(int count) {
        return films.values()
                .stream()
                .sorted(Comparator.comparing(Film::getRate).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public void checkFilmExist(int id) {
        if (films.get(id) == null) {
            throw new FilmNotFoundException(String.format("There are no such a film with id=%d: %s", id, films.get(id)));
        }
    }
}

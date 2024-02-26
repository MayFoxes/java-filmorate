package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    @Override
    public Film findById(Integer id) {
        return filmStorage.findById(id);
    }

    @Override
    public Film create(Film film) {
        return filmStorage.create(film);
    }

    @Override
    public Film update(Film film) {
        return filmStorage.update(film);
    }

    @Override
    public Film likeFilm(Integer id, Integer userId) {
        userStorage.checkUserExist(userId);
        userStorage.findById(userId).getFilmsLikes().add(id);

        return filmStorage.likeFilm(id, userId);
    }

    @Override
    public Film dislikeFilm(Integer id, Integer userId) {
        filmStorage.checkFilmExist(id);
        userStorage.checkUserExist(userId);
        checkUserLikedFilm(id, userId);
        userStorage.findById(userId).getFilmsLikes().remove(id);

        return filmStorage.deleteLike(id, userId);
    }

    @Override
    public List<Film> getPopular(Integer count) {
        return filmStorage.getPopular(count);
    }

    private void checkUserLikedFilm(Integer id, Integer userId) {
        if (!filmStorage.findById(id).getLikes().contains(userId) &&
                !userStorage.findById(userId).getFilmsLikes().contains(id)) {
            throw new FilmNotFoundException(String.format("User id:%d have not previously liked that film id:%d.", userId, id));
        }
    }
}

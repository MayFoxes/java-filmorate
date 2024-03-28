package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public interface GenreStorage {

    Genre makeGenre(ResultSet rs) throws SQLException;

    Genre getGenre(Integer id);

    Collection<Genre> getAllGenres();

    void checkGenreExist(Integer id);

}

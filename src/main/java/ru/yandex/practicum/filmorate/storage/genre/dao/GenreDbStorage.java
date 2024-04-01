package ru.yandex.practicum.filmorate.storage.genre.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Component
@Primary
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre makeGenre(ResultSet rs) throws SQLException {
        return Genre.builder()
                .id(rs.getInt("GENRE_ID"))
                .name(rs.getString("GENRE_NAME"))
                .build();
    }

    @Override
    public Genre getGenre(Integer id) {
        checkGenreExist(id);
        String sql = "SELECT * FROM GENRE WHERE GENRE_ID=?;";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeGenre(rs), id);

    }

    @Override
    public Collection<Genre> getAllGenres() {
        String sql = "SELECT * FROM GENRE ORDER BY GENRE_ID;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }

    @Override
    public void checkGenreExist(Integer id) {
        String sqlQuery = "SELECT COUNT(*) FROM GENRE WHERE GENRE_ID=?";

        Optional.ofNullable(jdbcTemplate.queryForObject(sqlQuery, Integer.class, id))
                .filter(result -> result == 1)
                .orElseThrow(() -> new NotFoundException(String.format("No such genre exist with this id:%s.", id)));
    }
}

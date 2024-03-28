package ru.yandex.practicum.filmorate.storage.mpa.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Component
@Primary
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa makeMpa(ResultSet rs) throws SQLException {
        return Mpa.builder()
                .id(rs.getInt("RATING_ID"))
                .name(rs.getString("RATING_NAME"))
                .build();
    }

    @Override
    public Mpa getMpa(Integer id) {
        checkMpaExist(id);
        String sql = "SELECT * FROM RATING WHERE RATING_ID=?;";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeMpa(rs), id);
    }


    @Override
    public Collection<Mpa> getAllMpa() {
        String sql = "SELECT * FROM RATING;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs));
    }

    @Override
    public void checkMpaExist(Integer id) {
        String sqlQuery = "SELECT COUNT(*) FROM RATING WHERE RATING_ID=?;";
        Optional.ofNullable(jdbcTemplate.queryForObject(sqlQuery, Integer.class, id))
                .filter(result -> result == 1)
                .orElseThrow(() -> new NotFoundException(String.format("No such MPA rating film exist with this id:%s.", id)));
    }

}

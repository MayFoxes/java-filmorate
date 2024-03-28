package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public interface MpaStorage {

    Mpa makeMpa(ResultSet rs) throws SQLException;

    Mpa getMpa(Integer id);

    Collection<Mpa> getAllMpa();

    void checkMpaExist(Integer id);
}

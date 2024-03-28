package ru.yandex.practicum.filmorate.service.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MpaServiceImpl implements MpaService {

    private final MpaStorage mpaStorage;

    @Override
    public Mpa getMpa(Integer id) {
        return mpaStorage.getMpa(id);
    }

    @Override
    public Collection<Mpa> getAllMpa() {
        return mpaStorage.getAllMpa();
    }
}

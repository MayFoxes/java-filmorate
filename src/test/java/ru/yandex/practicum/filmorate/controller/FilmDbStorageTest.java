package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {

    private final FilmStorage filmDbStorage;
    private final UserStorage userDbStorage;

    @Test
    public void addFilmTest() {

        Film newFilm = createDefaultFilm();
        Film film = filmDbStorage.addFilm(newFilm);

        newFilm.setId(film.getId());

        compare(newFilm, film);
    }

    @Test
    public void updateFilmTest() {

        Film newFilm = createDefaultFilm();
        Film film = filmDbStorage.addFilm(newFilm);

        film.setName("NewFilm");
        film.setDescription("NewDescr");
        film.setReleaseDate(LocalDate.of(2001, 1, 1));
        film.setDuration(111);
        filmDbStorage.updateFilm(film);

        Film savedFilm = filmDbStorage.getFilmById(film.getId());

        compare(film, savedFilm);
    }

    @Test
    public void addLikeTest() {

        User user = userDbStorage.addUser(User.builder().email("user@mail.ru").login("User").name("User").birthday(LocalDate.of(1980, 12, 12)).build());


        Film newFilm = filmDbStorage.addFilm(createDefaultFilm());

        filmDbStorage.addLike(newFilm.getId(), user.getId());

        List<Film> likes = new ArrayList<>(filmDbStorage.getPopular(10));

        assertTrue(likes.contains(newFilm));

    }

    @Test
    public void deleteLikeTest() {

        User user = userDbStorage.addUser(User.builder().email("user2@mail.ru").login("User2").name("User2").birthday(LocalDate.of(1980, 12, 12)).build());
        User user2 = userDbStorage.addUser(User.builder().email("user3@mail.ru").login("User3").name("User3").birthday(LocalDate.of(1980, 12, 12)).build());

        Film newFilm1 = filmDbStorage.addFilm(createDefaultFilm());
        Film newFilm2 = filmDbStorage.addFilm(createDefaultFilm());

        filmDbStorage.addLike(newFilm1.getId(), user.getId());
        filmDbStorage.addLike(newFilm2.getId(), user.getId());
        filmDbStorage.addLike(newFilm2.getId(), user2.getId());

        List<Film> likes = new ArrayList<>(filmDbStorage.getPopular(1));

        assertTrue(likes.contains(newFilm2));

        filmDbStorage.deleteLike(newFilm2.getId(), user.getId());
        filmDbStorage.deleteLike(newFilm2.getId(), user2.getId());

        likes = new ArrayList<>(filmDbStorage.getPopular(1));

        assertFalse(likes.contains(newFilm2));
        assertTrue(likes.contains(newFilm1));
    }

    @Test
    public void deleteFilmTest() {

        Film newFilm = filmDbStorage.addFilm(createDefaultFilm());

        List<Film> likes = new ArrayList<>(filmDbStorage.getAllFilms());

        assertTrue(likes.contains(newFilm));

        filmDbStorage.deleteFilm(newFilm.getId());

        likes = new ArrayList<>(filmDbStorage.getAllFilms());

        assertFalse(likes.contains(newFilm));
    }

    @Test
    public void userRecommendations() {
        Film film_1 = createDefaultFilm();
        Film film_2 = film_1.toBuilder()
                .name("testFilm 2")
                .description("testFilm 2")
                .releaseDate(LocalDate.of(2001, 2, 2))
                .duration(122)
                .mpa(Mpa.builder()
                        .id(2)
                        .build())
                .build();
        Film film_3 = film_1.toBuilder()
                .name("testFilm 3")
                .description("testFilm 3")
                .releaseDate(LocalDate.of(2002, 3, 3))
                .duration(123)
                .mpa(Mpa.builder()
                        .id(3)
                        .build())
                .build();
        User user_1 = User.builder()
                .email("user_1@mail.ru")
                .login("user_1@mail.ru")
                .name("User 1")
                .birthday(LocalDate.of(2005, 8, 15))
                .build();
        User user_2 = user_1.toBuilder()
                .email("user_2@mail.ru")
                .login("user_2@mail.ru")
                .name("User 2")
                .birthday(LocalDate.of(2006, 9, 16))
                .build();
        User user_3 = user_1.toBuilder()
                .email("user_3@mail.ru")
                .login("user_3@mail.ru")
                .name("User 3")
                .birthday(LocalDate.of(2007, 9, 17))
                .build();

        film_1 = filmDbStorage.addFilm(film_1);
        film_2 = filmDbStorage.addFilm(film_2);
        film_3 = filmDbStorage.addFilm(film_3);
        user_1 = userDbStorage.addUser(user_1);
        user_2 = userDbStorage.addUser(user_2);
        user_3 = userDbStorage.addUser(user_3);

        filmDbStorage.addLike(film_1.getId(), user_1.getId());
        filmDbStorage.addLike(film_2.getId(), user_1.getId());

        filmDbStorage.addLike(film_1.getId(), user_2.getId());
        filmDbStorage.addLike(film_2.getId(), user_2.getId());
        filmDbStorage.addLike(film_3.getId(), user_2.getId());

        filmDbStorage.addLike(film_3.getId(), user_3.getId());

        Collection<Film> recommendedFilmsForUser3 = filmDbStorage.getUserRecommendations(user_3.getId());

        assertFalse(recommendedFilmsForUser3.isEmpty());
        assertTrue(recommendedFilmsForUser3.containsAll(List.of(film_1, film_2)));
        assertEquals(2, recommendedFilmsForUser3.size());

        Collection<Film> recommendedFilmsForUser1 = filmDbStorage.getUserRecommendations(user_1.getId());

        assertFalse(recommendedFilmsForUser1.isEmpty());
        assertTrue(recommendedFilmsForUser1.containsAll(List.of(film_3)));
        assertEquals(1, recommendedFilmsForUser1.size());
    }

    private Film createDefaultFilm() {
        return Film.builder()
                .name("testFilm")
                .description("testFilm")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(120).mpa(Mpa.builder()
                        .id(1)
                        .build())
                .build();
    }

    private void compare(Film film1, Film film2) {
        assertNotNull(film1);
        assertNotNull(film2);
        assertEquals(film1.getId(), film2.getId());
        assertEquals(film1.getName(), film2.getName());
        assertEquals(film1.getDescription(), film2.getDescription());
        assertEquals(film1.getDuration(), film2.getDuration());
        assertEquals(film1.getReleaseDate(), film2.getReleaseDate());
        assertEquals(1, film1.getMpa().getId());
        assertEquals(1, film2.getMpa().getId());
    }
}
package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTest {

    private static final LocalDate RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private static final LocalDate FUTURE_DATE = LocalDate.now().plusDays(1);
    private static final LocalDate BIRTHDAY = LocalDate.now().minusYears(20);
    private static final User VALID_USER = new User(1, "my@mail.ru", "login", "name", BIRTHDAY);
    private static final Film VALID_FILM = new Film(1, "film", RandomString.make(200), RELEASE_DATE, 100);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void postValidData() throws Exception {

        mockMvc.perform(post("/films")
                .content(objectMapper.writeValueAsString(VALID_FILM))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(VALID_USER))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturn200AndListOfValidFilms() throws Exception {

        mockMvc.perform(get("/films"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn200AndValidFilmById() throws Exception {

        mockMvc.perform(get("/films/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(VALID_FILM)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("film"))
                .andExpect(jsonPath("$.releaseDate").value(RELEASE_DATE.toString()))
                .andExpect(jsonPath("$.duration").value(100));
    }

    @Test
    public void shouldReturn200AndValidFilmOnPost() throws Exception {

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(VALID_FILM))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("film"))
                .andExpect(jsonPath("$.releaseDate").value(RELEASE_DATE.toString()))
                .andExpect(jsonPath("$.duration").value(100));
    }

    @Test
    public void shouldReturn400PostOnInvalidFilmName() throws Exception {

        Film film = new Film(1, "", RandomString.make(200), RELEASE_DATE, 100);

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn400PostOnInvalidDescriptionFilm() throws Exception {

        Film film = new Film(1, "film", RandomString.make(201), RELEASE_DATE, 100);

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn400PostOnInvalidFutureDateFilm() throws Exception {

        Film film = new Film(1, "film", RandomString.make(200), FUTURE_DATE, 100);

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn400PostOnInvalidReleaseDateFilm() throws Exception {

        Film film = new Film(1, "film", RandomString.make(200), RELEASE_DATE.minusDays(1), 100);
        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldReturn400PostOnInvalidNegativeFilmDuration() throws Exception {

        Film film = new Film(1, "film", RandomString.make(200), RELEASE_DATE, -100);
        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn200PutOnValidFilmUpdate() throws Exception {

        Film updatedFilm = new Film(1, "updated film", RandomString.make(200), RELEASE_DATE, 200);

        mockMvc.perform(put("/films")
                        .content(objectMapper.writeValueAsString(updatedFilm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("updated film"))
                .andExpect(jsonPath("$.releaseDate").value(RELEASE_DATE.toString()))
                .andExpect(jsonPath("$.duration").value(200));
    }

    @Test
    public void shouldReturn200PutOnFilmLike() throws Exception {

        mockMvc.perform(put("/films/1/like/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.likes.length()").value(1))
                .andExpect(jsonPath("$.likes[0]").value(1))
                .andExpect(jsonPath("$.rate").value(1));
    }

    @Test
    public void shouldReturn200PutOnFilmDeleteLike() throws Exception {

        mockMvc.perform(put("/films/1/like/1"));

        mockMvc.perform(delete("/films/1/like/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.likes.length()").value(0))
                .andExpect(jsonPath("$.rate").value(0));
    }

    @Test
    public void shouldReturn200WhenGetPopularFilm() throws Exception {

        mockMvc.perform(put("/films/1/like/1"));

        mockMvc.perform(get("/films/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        mockMvc.perform(get("/films/popular")
                        .param("count", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}
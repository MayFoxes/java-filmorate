package ru.yandex.practicum.filmorate.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FilmController.class)
class FilmControllerTest {

    Film film = new Film();
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        film.setId(1);
        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.of(2000, 12, 12));
        film.setDuration(1000);
    }

    @SneakyThrows
    @Test
    void testPostFilmValidation() {
        mockMvc.perform(post("/film")
                        .contentType("application/json")
                        .content(film.toString()))
                .andExpect(status().isOk());

        film.setId(2);
        film.setName("");

        mockMvc.perform(post("/film")
                        .contentType("application/json")
                        .content(film.toString()))
                .andExpect(status().is4xxClientError());

        film.setId(3);
        film.setName("name");
        film.setDescription("A character can be any letter, number, punctuation, special character, or space. Each of these characters takes up one byte of space in a computer's memory. Some Unicode characters, like emojis and some letters in non-Latin alphabets, take up two bytes of space and therefore count as two characters. Use our character counter tool below for an accurate count of your characters.");

        mockMvc.perform(post("/film")
                        .contentType("application/json")
                        .content(film.toString()))
                .andExpect(status().is4xxClientError());

        film.setId(4);
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(1000, 5, 5));

        mockMvc.perform(post("/film")
                        .contentType("application/json")
                        .content(film.toString()))
                .andExpect(status().is4xxClientError());

        film.setId(5);
        film.setReleaseDate(LocalDate.of(2000, 5, 5));
        film.setDuration(-1);

        mockMvc.perform(post("/film")
                        .contentType("application/json")
                        .content(film.toString()))
                .andExpect(status().is4xxClientError());

        String inValidFilm = "";

        mockMvc.perform(post("/film")
                        .contentType("application/json")
                        .content(inValidFilm))
                .andExpect(status().is4xxClientError());
    }

    @SneakyThrows
    @Test
    void testGetFilms() {
        mockMvc.perform(post("/film")
                        .contentType("application/json")
                        .content(film.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/films"))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void testPutFilmValidation() {
        mockMvc.perform(post("/film")
                        .contentType("application/json")
                        .content(film.toString()))
                .andExpect(status().isOk());

        film.setName("Updated film");

        mockMvc.perform(put("/film")
                        .contentType("application/json")
                        .content(film.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated film"));
    }
}

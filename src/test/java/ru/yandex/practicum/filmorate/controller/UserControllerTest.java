package ru.yandex.practicum.filmorate.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    User user = new User();
    UserController userController = new UserController();
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        user.setId(1);
        user.setLogin("Login");
        user.setEmail("Email@yandex.ru");
        user.setBirthday(LocalDate.of(2000, 12, 12));
        userController.addUser(user);
    }

    @SneakyThrows
    @Test
    void testUserPostValidation() {
        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(user.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Login"));

        user.setLogin("");
        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(user.toString()))
                .andExpect(status().is4xxClientError());

        user.setLogin("New login");
        user.setEmail("mail.com");

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(user.toString()))
                .andExpect(status().is4xxClientError());

        user.setEmail("NewMail@yandex.com");
        user.setBirthday(LocalDate.of(2500, 12, 12));

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(user.toString()))
                .andExpect(status().is4xxClientError());
    }

    @SneakyThrows
    @Test
    void testUserGet() {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void testPutUserValidation() {
        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(user.toString()))
                .andExpect(status().isOk());

        user.setName("Updated user");

        mockMvc.perform(put("/users")
                        .contentType("application/json")
                        .content(user.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated user"));
    }
}

package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private static final LocalDate BIRTHDAY = LocalDate.now().minusYears(20);
    private static final User VALID_USER = new User(1, "my@mail.ru", "login", "name", BIRTHDAY);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void postValidUser() throws Exception {

        mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(VALID_USER))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(VALID_USER))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturn200AndValidUserOnPost() throws Exception {
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(VALID_USER))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("my@mail.ru"))
                .andExpect(jsonPath("$.login").value("login"))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY.toString()));
    }

    @Test
    public void shouldReturn200PostOnEmptyUserName() throws Exception {

        User user = new User(1, "my@mail.ru", "login", "", BIRTHDAY);

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("login"));
    }

    @Test
    public void shouldReturn400PostOnEmptyUserLogin() throws Exception {

        User user = new User(1, "my@mail.ru", "", "", BIRTHDAY);

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn400PostInvalidMail() throws Exception {

        User user = new User(1, "mymail.ru", "login", "", BIRTHDAY);

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn200PutValidUserUpdate() throws Exception {

        User updatedUser = new User(1, "myUpdated@mail.ru", "UpdatedLogin", "", BIRTHDAY);

        mockMvc.perform(put("/users")
                        .content(objectMapper.writeValueAsString(updatedUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("myUpdated@mail.ru"))
                .andExpect(jsonPath("$.login").value("UpdatedLogin"))
                .andExpect(jsonPath("$.name").value("UpdatedLogin"))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY.toString()));
    }

    @Test
    public void shouldReturn200PutAndListOfFriends() throws Exception {

        mockMvc.perform(put("/users/1/friends/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[0].friends.length()").value(1))
                .andExpect(jsonPath("$[1].friends.length()").value(1))
                .andExpect(jsonPath("$[0].friends[0]").value(2))
                .andExpect(jsonPath("$[1].friends[0]").value(1));
    }

    @Test
    public void shouldReturn200Delete() throws Exception {

        mockMvc.perform(put("/users/1/friends/2"));

        mockMvc.perform(delete("/users/1/friends/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[1].id").value(1))
                .andExpect(jsonPath("$[0].friends.length()").value(0))
                .andExpect(jsonPath("$[1].friends.length()").value(0));
    }

    @Test
    public void shouldReturn200andGetListOfFriend() throws Exception {

        mockMvc.perform(put("/users/1/friends/2"));

        mockMvc.perform(get("/users/1/friends"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(2));
    }
}

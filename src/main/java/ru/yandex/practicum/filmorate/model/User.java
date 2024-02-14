package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    @Email(message = "Email must not be empty and must have \"@\" ")
    private String email;
    @NotNull(message = "Login must not be empty or blank")
    @NotBlank(message = "Login must not be empty or blank")
    private String login;
    private String name;
    @PastOrPresent(message = "Date of birth must not be in the future")
    private LocalDate birthday;

    public String toString() {
        return String.format("{\"id\":%d,\"login\":\"%s\",\"name\":\"%s\",\"email\":\"%s\",\"birthday\":\"%s\"}",
                this.id, this.login, this.name, this.email, this.birthday);
    }
}

package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString
public class User {
    private Integer id = 0;

    @Email(message = "Email must not be empty and must have \"@\" ")
    private String email;

    @NotNull(message = "Login must not be empty or blank")
    @NotBlank(message = "Login must not be empty or blank")
    private String login;

    private String name;

    @PastOrPresent(message = "Date of birth must not be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private final Set<Integer> filmsLikes = new HashSet<>();
    private final Set<Integer> friends = new HashSet<>();

    public User(Integer id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}

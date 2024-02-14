package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.FilmAnnotation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NotEmpty(message = "Film name can not be empty")
    private String name;
    @Size(max = 200, message = "Maximum description length is 200 char")
    @NotNull
    private String description;
    @FilmAnnotation
    private LocalDate releaseDate;
    @Positive(message = "The length of the film must be positive")
    private long duration;

    public String toString() {
        return String.format("{\"id\":%d,\"name\":\"%s\",\"description\":\"%s\",\"releaseDate\":\"%s\",\"duration\":%d}",
                this.id, this.name, this.description, this.releaseDate, this.duration);
    }
}

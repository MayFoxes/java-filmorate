package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import ru.yandex.practicum.filmorate.annotation.FilmAnnotation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString
public class Film {

    private Integer id = 0;

    @NotEmpty(message = "Film name can not be empty")
    private String name;

    @Size(max = 200, message = "Maximum description length is 200 char")
    @NotNull
    private String description;

    @FilmAnnotation
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @Positive(message = "The length of the film must be positive")
    private int duration;

    @NotNull
    private int rate = 0;

    private Set<Integer> likes = new HashSet<>();

    public Film(Integer id, String name, String description, LocalDate releaseDate, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Set<Integer> getLikes() {
        return new HashSet<>(likes);
    }

    public void setLikes(Set<Integer> likes) {
        this.likes = likes;
    }
}

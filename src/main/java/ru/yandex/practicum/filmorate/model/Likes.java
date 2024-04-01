package ru.yandex.practicum.filmorate.model;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Likes {

    @NotNull
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer filmId;
}

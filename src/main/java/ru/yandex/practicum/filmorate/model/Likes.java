package ru.yandex.practicum.filmorate.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class Likes {

    @NotNull
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer filmId;
}

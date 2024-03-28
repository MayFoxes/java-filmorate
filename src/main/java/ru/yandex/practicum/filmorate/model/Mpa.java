package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode
public class Mpa {

    @NotNull
    private Integer id;

    @NotNull
    private String name;
}
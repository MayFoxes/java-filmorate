package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidatorException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    private int id;
    private final HashMap<Integer, User> users = new HashMap<>();

    private void idGenerated() {
        id++;
    }

    @RequestMapping("/users")
    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping(value = "/users")
    public User addUser(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        idGenerated();
        user.setId(id);
        users.put(user.getId(), user);
        log.info("User added: {}", user);
        return user;
    }

    @PutMapping(value = "/users")
    public User updateUser(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("User updated: {}", user);
            return user;
        } else {
            throw new ValidatorException("Wrong id / do not have this user");

        }
    }
}

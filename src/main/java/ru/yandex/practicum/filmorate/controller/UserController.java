package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/users")
    public User addUser(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping(value = "/users")
    public User updateUser(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public List<User> addFriend(
            @PathVariable int id,
            @PathVariable int friendId
    ) {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/users/{id}/friends/{friendId}")
    public List<User> deleteFriend(
            @PathVariable int id,
            @PathVariable int friendId
    ) {
        return userService.deleteFriend(friendId, id);
    }

    @GetMapping(value = "/users")
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping(value = "/users/{id}")
    public User findUserById(@PathVariable int id) {
        return userService.findById(id);
    }

    @GetMapping(value = "/users/{id}/friends")
    public List<User> findFriends(@PathVariable int id) {
        return userService.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}

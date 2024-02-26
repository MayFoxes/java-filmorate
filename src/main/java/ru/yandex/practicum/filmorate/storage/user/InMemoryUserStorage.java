package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private int id;
    private final HashMap<Integer, User> users = new HashMap<>();

    private int idGenerated() {
        return ++id;
    }

    @Override
    public User create(User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(idGenerated());
        users.put(user.getId(), user);
        log.info("User added: {}", user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        checkUserExist(user.getId());
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("User updated: {}", user);
        return user;
    }

    @Override
    public User findById(int uId) {
        return users.get(uId);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<User> addFriend(int id, int friendId) {
        users.get(id).getFriends().add(friendId);
        users.get(friendId).getFriends().add(id);
        log.info("User{} add friend:{}", users.get(id), users.get(friendId));
        return List.of(users.get(id), users.get(friendId));
    }

    @Override
    public List<User> deleteFriend(int id, int friendId) {
        users.get(id).getFriends().remove(friendId);
        users.get(friendId).getFriends().remove(id);
        log.info("User{} delete friend:{}", users.get(id), users.get(friendId));
        return List.of(users.get(id), users.get(friendId));
    }

    @Override
    public List<User> getFriends(int id) {
        return users.get(id).getFriends().stream()
                .map(users::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) {
        return users.get(id).getFriends().stream()
                .filter(users.get(otherId).getFriends()::contains)
                .map(users::get)
                .collect(Collectors.toList());
    }

    @Override
    public void checkUserExist(int id) {
        if (users.get(id) == null) {
            throw new UserNotFoundException(String.format("There are no such a user with id=%d: %s", id, users.get(id)));
        }
    }
}

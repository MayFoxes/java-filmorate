package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int id);

    User create(User user);

    User update(User user);

    List<User> addFriend(int id, int friendId);

    List<User> deleteFriend(int id, int friendId);

    List<User> getFriends(int id);

    List<User> getCommonFriends(int id, int otherId);
}

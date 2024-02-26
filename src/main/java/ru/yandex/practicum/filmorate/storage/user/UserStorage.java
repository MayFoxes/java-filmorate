package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User create(User user);

    User findById(int id);

    User updateUser(User user);

    List<User> findAll();

    void checkUserExist(int id);

    List<User> addFriend(int id, int friendId);

    List<User> getFriends(int id);

    List<User> deleteFriend(int id, int friendId);

    List<User> getCommonFriends(int id, int otherId);
}

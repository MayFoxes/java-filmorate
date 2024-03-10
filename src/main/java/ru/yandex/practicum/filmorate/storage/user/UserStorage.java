package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User create(User user);

    User findById(Integer id);

    User updateUser(User user);

    List<User> findAll();

    void checkUserExist(Integer id);

    List<User> addFriend(Integer id, Integer friendId);

    List<User> getFriends(Integer id);

    List<User> deleteFriend(Integer id, Integer friendId);

    List<User> getCommonFriends(Integer id, Integer otherId);
}

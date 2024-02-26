package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    @Override
    public List<User> findAll() {
        return userStorage.findAll();
    }

    @Override
    public User findById(Integer id) {
        userStorage.checkUserExist(id);
        return userStorage.findById(id);
    }

    @Override
    public User create(User user) {
        return userStorage.create(user);
    }

    @Override
    public User update(User user) {
        return userStorage.updateUser(user);
    }

    @Override
    public List<User> addFriend(Integer id, Integer friendId) {
        userStorage.checkUserExist(id);
        userStorage.checkUserExist(friendId);

        return userStorage.addFriend(id, friendId);
    }

    @Override
    public List<User> deleteFriend(Integer id, Integer friendId) {
        userStorage.checkUserExist(id);
        userStorage.checkUserExist(friendId);

        return userStorage.deleteFriend(id, friendId);
    }

    @Override
    public List<User> getFriends(Integer id) {
        userStorage.checkUserExist(id);

        return userStorage.getFriends(id);
    }

    @Override
    public List<User> getCommonFriends(Integer id, Integer otherId) {
        userStorage.checkUserExist(id);
        userStorage.checkUserExist(otherId);

        return userStorage.getCommonFriends(id, otherId);
    }
}

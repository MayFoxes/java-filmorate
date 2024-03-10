# java-filmorate
Template repository for Filmorate project.


![DB Schema.](/ER-diagram.png)


Получение всех фильмов:

```
SELECT *
FROM film;
```

Получение фильма по Id:

```
SELECT *
FROM film
WHERE id = 1;
```

Получение всех пользователей:

```
SELECT *
FROM user;
```

Получение пользователя по Id:

```
SELECT *
FROM user
WHERE id = 1;
```

Получение списка друзей:

```
SELECT user_2.name
FROM user
INNER JOIN friend on user.user_id = friend.user_id
INNER JOIN user as user_2 on user_2.user_id = friend.friend_id
WHERE user.user_id = 1;
```

Получение 10 фильмов с максимальным кол-вом лайков:
/через таблицу like

```
SELECT  film_name, COUNT(like.film_id)
FROM film
INNER JOIN film_user_like as like on film.film_id = like.film_id
GROUP BY film_name
ORDER BY COUNT(like.film_id) DESC
LIMIT 10;
```
/через внутренее поле таблицы film - likes

```
SELECT  film_name
FROM film
GROUP BY film_name
ORDER BY likes desc
LIMIT 10;
```

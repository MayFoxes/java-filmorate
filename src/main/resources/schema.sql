CREATE TABLE IF NOT EXISTS RATING
(
    RATING_ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    RATING_NAME          VARCHAR(40)

);

CREATE TABLE IF NOT EXISTS FILMS
(
    FILM_ID      INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    TITLE          VARCHAR(100) NOT NULL,
    DESCRIPTION   VARCHAR(200) NOT NULL,
    RELEASE_DATE  DATE    NOT NULL,
    DURATION      INTEGER      NOT NULL,
    RATING INTEGER REFERENCES RATING (RATING_ID)
);

CREATE TABLE IF NOT EXISTS USERS
(
    USER_ID  INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    EMAIL    VARCHAR(255) NOT NULL UNIQUE,
    LOGIN    VARCHAR(50) NOT NULL UNIQUE,
    NAME     VARCHAR(50),
    BIRTHDAY DATE        NOT NULL

);

CREATE TABLE IF NOT EXISTS USER_FILM
(
    USER_ID INTEGER NOT NULL REFERENCES USERS (USER_ID) ON DELETE CASCADE,
    FILM_ID INTEGER NOT NULL REFERENCES FILMS (FILM_ID) ON DELETE CASCADE,
    CONSTRAINT USER_FILM_PK PRIMARY KEY (USER_ID, FILM_ID)

);

CREATE TABLE IF NOT EXISTS GENRE
(
    GENRE_ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    GENRE_NAME    VARCHAR(40) NOT NULL

);

CREATE TABLE IF NOT EXISTS FILM_GENRE
(
    FILM_ID  INTEGER NOT NULL REFERENCES FILMS (FILM_ID) ON DELETE CASCADE,
    GENRE_ID INTEGER NOT NULL REFERENCES GENRE (GENRE_ID) ON DELETE CASCADE,
    CONSTRAINT FILM_GENRE_PK PRIMARY KEY (FILM_ID, GENRE_ID)

);

CREATE TABLE IF NOT EXISTS FRIENDS
(
    USER_ID         INTEGER NOT NULL REFERENCES USERS (USER_ID) ON DELETE CASCADE,
    FRIEND_ID       INTEGER NOT NULL REFERENCES USERS (USER_ID) ON DELETE CASCADE,
    CONSTRAINT FRIENDS_PK PRIMARY KEY (USER_ID, FRIEND_ID)
);
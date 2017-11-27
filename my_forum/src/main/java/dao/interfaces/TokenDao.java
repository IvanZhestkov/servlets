package dao.interfaces;

import pojo.user.Token;
import pojo.user.User;

import java.util.Date;

/**
 * Интерфейс, описывающий методы для работы с токенами.
 */
public interface TokenDao {

    /** Создание записи*/
    void create(Token token);

    /** Возвращает объект по первичному ключу uuid*/
    Token readToken(String UUId);

    /** Возвращает пользователя по первичному ключу uuid*/
    User readUser(String UUId);

    /** Удаление записи о пользователе по токену*/
    void deleteUser(Token token);

    /** Удаление токена*/
    void deleteToken(Token token);

    /** Удаление токена по дате*/
    void deleteToken(Date date);
}

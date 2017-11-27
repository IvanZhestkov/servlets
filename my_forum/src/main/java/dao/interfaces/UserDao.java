package dao.interfaces;

import pojo.ForumMessage;
import pojo.Topic;
import pojo.user.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс, описывающий методы для работы с пользователями.
 */
public interface UserDao {

    /** Создание записи о пользователе*/
    void createUser(User user);

    /** Добавление записи uuid rememberme*/
    void addRememberUuid(User user, String uuid);

    /** Удаление записи uuid rememberme*/
    void deleteRememberUuid(int userId);

    /** Возвращает объект пользователя*/
    User getUser(int userId);

    User getUserByName(String username);

    User getUser(String username, String password);

    User getUserByEmail(String email);

    User getUserByTokenRemember(String token);

    List<Topic> getTopics(User user, int number);

    User getUserForNotif(int userId);

    /** Обновление записи о пользователе*/
    void updateUser(User user);

    /** Повышение рейтинга пользователя*/
    void updateRating(User user);

    /** Удаление пользователя*/
    void deleteUser(User user);

    /**Получение количества сообщений пользователя на сайте*/
    int getAnswersNumber(User user);

    int getTopicsNumber(User user);

    Topic getLastTopicByUser(User user);

    List<User> getAllUsers() throws SQLException;

    ForumMessage getLastMessage(User user);

}

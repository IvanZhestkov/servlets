package dao.interfaces;

import pojo.user.User;

/**
 * Интерфейс, описывающий методы для сборки статистики.
 */
public interface StatsDao {
    /** Получение кол-ва всех сообщений на сайте*/
    int getForumMessagesNumber();

    /** Получение кол-ва всех тем на сайте*/
    int getTopicsNumber();

    /** Получение кол-ва всех зарегистрированных пользователей*/
    int getUsersNumber();

    /** Получение последнего зарегистрированного пользователя*/
    User getLastUser();
}

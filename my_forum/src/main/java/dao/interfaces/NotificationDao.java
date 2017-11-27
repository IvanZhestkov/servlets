package dao.interfaces;

import pojo.ForumMessage;
import pojo.Notification;
import pojo.Topic;
import pojo.user.User;

import java.util.List;

/**
 * Интерфейс, описывающий методы для работы с записями уведомлений о подписке к теме
 */
public interface NotificationDao {

    /** Подписка пользователя*/
    void feed(User user, Topic topic);

    /**Отписка пользователя*/
    void unfeed(User user, Topic topic);

    /**Создание уведомления пользователя*/
    void createNotification(User user, Topic topic, ForumMessage message);

    /**Извлечение списка уведомлений для пользователя*/
    List<Notification> getNotifications(User user);

    List<User> getUsers(Topic topic);

    boolean isFeeded(User user, Topic topic);
}

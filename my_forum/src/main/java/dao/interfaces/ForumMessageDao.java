package dao.interfaces;

import pojo.ForumMessage;
import pojo.Topic;
import pojo.user.User;

/**
 * Интерфейс, описывающий методы для работы с отдельными
 * сообщениями форума
 */
public interface ForumMessageDao {

    /** Создание записи о сообщении*/
    void createForumMessage(ForumMessage forumMessage);

    /** Возвращает объект сообщения по первичному ключу forumMessageId*/
    ForumMessage getForumMessage(int forumMessageId);

    /** Обновляет информацию о сообщении */
    void updateForumMessage(ForumMessage forumMessage);

    /** Удаление записи о сообщении */
    void deleteForumMessage(ForumMessage forumMessage);

    /** Удаление всех сообщений пользователя под темой*/
    void deleteAllForumMessage(User user, Topic topic);

    ForumMessage getMessageWithoutAuthor(int messageId);
}

package dao.interfaces;

import pojo.ForumMessage;
import pojo.Tag;
import pojo.Topic;

import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс, описывающий методы для работы с темами.
 */
public interface TopicDao {

    /** Создание новой темы и её записи*/
    void createTopic(Topic topic);

    /** Получение объекта темы по её первичному ключу topicId*/
    Topic getTopicById(int topicId);

    Topic getTopicByIdWithoutAuthor(int topicId);


    /** Получение количества сообщений в теме*/
    int getAnswersCount(Topic topic);

    /** Получение последнего сообщения топика*/
    ForumMessage getLastMessage(Topic topic);

    /** Обновление информации о теме в записи*/
    void updateTopic(Topic topic);

    /** Обновление статуса темы*/
    void updateStatus(Topic topic);

    /** Удаление записи о теме из базы*/
    void deleteTopic(Topic topic);

    List<ForumMessage> getAllMessages(Topic topic) throws SQLException;

    List<Tag> getTags(Topic topic);
}

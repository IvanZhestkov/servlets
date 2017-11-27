package dao.interfaces;

import pojo.Tag;
import pojo.Topic;

import java.util.List;

/**
 * Класс, для работы с записями Tag
 */
public interface TagDao {

    void addTag(Tag tag, Topic topic);

    void deleteTag(Tag tag);

    void deleteTagTopic(Topic topic);

    List<Topic> getTopics(String tagName);

}

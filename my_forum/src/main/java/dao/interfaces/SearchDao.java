package dao.interfaces;

import pojo.Section;
import pojo.Topic;

import java.util.List;

/**
 * Интерфейс, описывающий методы поиска.
 */
public interface SearchDao {

    /**Поиск раздела по ключевому слову*/
    List<Section> searchSection(String key);

    /**Поиск темы по ключевому слову*/
    List<Topic> searchTopic(String key);
}

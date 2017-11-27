package dao.interfaces;

import pojo.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс, описывающий методы для обращения
 * к разделам форума.
 */
public interface SectionDao {

    /** Создание новой записи о разделе*/
    void createSection(Section section);

    /** Создание подраздела*/
    void createSubsection(Section section);

    /** Возвращает объект раздела с первичным ключом sectionId*/
    Section getSection(int sectionId);

    /** Возвращает последнее сообщение раздела*/
    Topic getLastTopic(Section section);

    /** Подсчитать общее кол-во сообщений в разделе*/
    long getCountOfMessages(Section section);

    /** Изменение информации о разделе */
    void updateSection(Section section);

    /** Изменение родительского раздела у подраздела */
    void updateSubsection(Section section);

    /** Удаление записи о разделе*/
    void deleteSection(Section section);

    List<Topic> getAllTopics(Section section);

    List<Topic> getAllTopicsPaging(Section section, int page, int countOfTopics);

    int getPagesNum(Section section, int numberOfTopicsOnPage);

    List<Section> getAllSections(Forum forum) throws SQLException;

    List<Section> getAllSubsections(Section section) throws SQLException;
}

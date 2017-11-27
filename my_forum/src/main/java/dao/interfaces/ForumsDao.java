package dao.interfaces;

import pojo.Forum;

import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс, определяющий методы для работы с отдельным форумом.
 */
public interface ForumsDao {

    /** Создание нового форума */
    void createForum(Forum forum);

    /** Чтение форума по первичному ключу forumId*/
    Forum getForum(int forumId);

    List<Forum> getAllForums() throws SQLException;

    void updateForum(Forum forum);

    /** Удаление форума с переносом его (отдельных) разделов*/
    void deleteForum(Forum forum);
}

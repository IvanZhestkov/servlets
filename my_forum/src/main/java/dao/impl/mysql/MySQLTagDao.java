package dao.impl.mysql;

import org.apache.log4j.Logger;
import dao.interfaces.TagDao;
import pojo.Tag;
import pojo.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализизующий TagDao
 * @see TagDao
 */
public class MySQLTagDao extends MySQLDao implements TagDao {

    Logger log = Logger.getLogger(MySQLTagDao.class.getName());
    static MySQLTopicsDao topicsDao = new MySQLTopicsDao();

    public void addTag(Tag tag, Topic topic) {
        String sql = "INSERT INTO tags (tag_name, tag_topic_id) VALUES(?,?)";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, tag.getTagName());
            pstmt.setInt(2, topic.getTopicId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в создании тега", sqle);
            sqle.printStackTrace();
        } finally {
            try {
                connection.close();
                pstmt.close();
            } catch (SQLException sqle) {
                log.error("Ошибка в закрытии соединения", sqle);
                sqle.printStackTrace();
            }
        }
    }

    public void deleteTag(Tag tag) {
        String sql = "DELETE FROM tags WHERE tag_id = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, tag.getTagId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в удалении тега " + tag.getTagName(), sqle);
            sqle.printStackTrace();
        } finally {
            try {
                connection.close();
                pstmt.close();
            } catch (SQLException sqle) {
                log.error("Ошибка в закрытии соединения", sqle);
                sqle.printStackTrace();
            }
        }
    }

    public void deleteTagTopic(Topic topic) {
        String sql = "DELETE FROM tags WHERE tag_topic_id = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, topic.getTopicId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в удалении тега для темы" + topic.getTopicName(), sqle);
            sqle.printStackTrace();
        } finally {
            try {
                connection.close();
                pstmt.close();
            } catch (SQLException sqle) {
                log.error("Ошибка в закрытии соединения", sqle);
                sqle.printStackTrace();
            }
        }
    }

    public List<Topic> getTopics(String tagName) {
        String sql = "SELECT tag_topic_id FROM tags WHERE tag_name = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        List<Topic> topicList = new ArrayList<Topic>();
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, tagName);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                topicList.add(topicsDao.getTopicById(rs.getInt("tag_topic_id")));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в получении тем по тегу "+tagName, sqle);
            sqle.printStackTrace();
        } finally {
            try {
                connection.close();
                pstmt.close();
            } catch (SQLException sqle) {
                log.error("Ошибка в закрытии соединения", sqle);
                sqle.printStackTrace();
            }
        }
        return topicList;
    }
}

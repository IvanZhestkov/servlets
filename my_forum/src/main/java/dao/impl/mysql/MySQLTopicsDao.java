package dao.impl.mysql;

import org.apache.log4j.Logger;
import dao.interfaces.TopicDao;
import pojo.ForumMessage;
import pojo.Tag;
import pojo.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Класс, реализующий интерфейс TopicDao
 */
public class MySQLTopicsDao extends MySQLDao implements TopicDao {

    Logger log = Logger.getLogger(MySQLTopicsDao.class.getName());
    static MySQLUserDao userDao = new MySQLUserDao();
    static MySQLForumMessageDao  forumMessageDao = new MySQLForumMessageDao();
    static MySQLSectionDao sectionDao = new MySQLSectionDao();
    static MySQLTagDao tagDao = new MySQLTagDao();
    static SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");

    public void createTopic(Topic topic) {
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement("INSERT INTO topics "
                    + "(topic_name, topic_section_id, topic_author_id, topic_text)"
                    + "VALUES(?,?,?,?)");
            pstmt.setString(1, topic.getTopicName());
            pstmt.setInt(2, topic.getTopicSection().getSectionId());
            pstmt.setInt(3, topic.getTopicAuthor().getUserId());
            pstmt.setString(4, topic.getTopicText());
            pstmt.execute();
            Iterator<Tag> tagIterator = topic.getTopicTags().iterator();
            while(tagIterator.hasNext()) {
                tagDao.addTag(tagIterator.next(), userDao.getLastTopicByUser(topic.getTopicAuthor()));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в создании темы", sqle);
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

    public Topic getTopicById(int topicId) {
        String sql = "SELECT * FROM topics WHERE topic_id = ?";
        Topic topic = null;
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, topicId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                topic = new Topic();
                topic.setTopicId(topicId);
                topic.setTopicName(rs.getString("topic_name"));
                topic.setTopicText(rs.getString("topic_text"));
                topic.setTopicAuthor(userDao.getUserForNotif(rs.getInt("topic_author_id")));
                topic.setTopicSection(sectionDao.getSection(rs.getInt("topic_section_id")));
                topic.setTopicStatus(rs.getInt("topic_status"));
                topic.setTopicViews(rs.getInt("topic_views"));
                topic.setCreateDatetime(rs.getDate("topic_create_datetime"));
                topic.setEditDatetime(rs.getDate("topic_edit_datetime"));
                topic.setTopicAnswersCount(getAnswersCount(topic));
                topic.setCreateTime(localDateFormat.format(topic.getCreateDatetime()));
                if(getTags(topic).iterator().hasNext()) {
                    topic.setTopicTags(getTags(topic));
                }
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в чтении темы " +
                    topic.getTopicName() + " раздела " +
                    topic.getTopicSection().getSectionId(), sqle);
            sqle.printStackTrace();
        } finally {
            try {
                connection.close();
                pstmt.close();
            }
            catch(SQLException sqle){
                log.error("Ошибка в закрытии соединения",sqle);
                sqle.printStackTrace();
            }
        }
        return topic;
    }

    public Topic getTopicByIdWithoutAuthor(int topicId) {
        String sql = "SELECT * FROM topics WHERE topic_id = ?";
        Topic topic = null;
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, topicId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                topic = new Topic();
                topic.setTopicId(topicId);
                topic.setTopicName(rs.getString("topic_name"));
                topic.setTopicText(rs.getString("topic_text"));
                topic.setTopicSection(sectionDao.getSection(rs.getInt("topic_section_id")));
                topic.setTopicStatus(rs.getInt("topic_status"));
                topic.setTopicViews(rs.getInt("topic_views"));
                topic.setCreateDatetime(rs.getDate("topic_create_datetime"));
                topic.setEditDatetime(rs.getDate("topic_edit_datetime"));
                topic.setTopicAnswersCount(getAnswersCount(topic));
                if(getTags(topic).iterator().hasNext()) {
                    topic.setTopicTags(getTags(topic));
                }
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в чтении темы " +
                    topic.getTopicName() + " раздела " +
                    topic.getTopicSection().getSectionId(), sqle);
            sqle.printStackTrace();
        } finally {
            try {
                connection.close();
                pstmt.close();
            }
            catch(SQLException sqle){
                log.error("Ошибка в закрытии соединения",sqle);
                sqle.printStackTrace();
            }
        }
        return topic;
    }

    public int getAnswersCount(Topic topic) {
        String sql = "SELECT COUNT(message_id) 'count' FROM forum_messages WHERE message_topic_id = ?;";
        int answersCount = 0;
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, topic.getTopicId());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                answersCount = rs.getInt("count");
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в чтении последнего сообщения темы " +
                    topic.getTopicName() + " раздела " +
                    topic.getTopicSection().getSectionName(), sqle);
            sqle.printStackTrace();
        } finally {
            try {
                connection.close();
                pstmt.close();
            }
            catch(SQLException sqle){
                log.error("Ошибка в закрытии соединения",sqle);
                sqle.printStackTrace();
            }
        }
        return answersCount;
    }

    public ForumMessage getLastMessage(Topic topic) {
        String sql = "SELECT message_id" +
                "FROM forum_messages" +
                "WHERE message_topic_id = ?" +
                "ORDER BY message_datetime DESC" +
                "LIMIT 1;";
        ForumMessage forumMessage = null;
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, topic.getTopicId());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                forumMessage = forumMessageDao.getForumMessage(rs.getInt("message_id"));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в чтении последнего сообщения темы " +
                    topic.getTopicName() + " раздела " +
                    topic.getTopicSection().getSectionName(), sqle);
            sqle.printStackTrace();
        } finally {
            try {
                connection.close();
                pstmt.close();
            }
            catch(SQLException sqle){
                log.error("Ошибка в закрытии соединения",sqle);
                sqle.printStackTrace();
            }
        }
        return forumMessage;
    }

    public void updateTopic(Topic topic) {
        String sql = "UPDATE topics SET " +
                "topic_name = ?," +
                "topic_section_id = ?," +
                "topic_text = ? WHERE topic_id = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, topic.getTopicName());
            pstmt.setInt(2, topic.getTopicId());
            pstmt.setString(3, topic.getTopicText());
            pstmt.setInt(4, topic.getTopicId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в изменении темы " + topic.getTopicName(), sqle);
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

    public void updateStatus(Topic topic) {
        String sql = "UPDATE topics SET topic_status = ? WHERE topic_id = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, topic.getTopicStatus());
            pstmt.setInt(2, topic.getTopicId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в изменении темы " + topic.getTopicName(), sqle);
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

    public void deleteTopic(Topic topic) {
        String sql = "DELETE FROM topics WHERE topic_id = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, topic.getTopicId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в удалении темы " + topic.getTopicName(), sqle);
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

    public List<ForumMessage> getAllMessages(Topic topic) throws SQLException {
        List<ForumMessage> forumMessageList = new ArrayList<ForumMessage>();
        ForumMessage forumMessage = null;
        String sql = "SELECT * FROM forum_messages WHERE message_topic_id=?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, topic.getTopicId());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                forumMessage = forumMessageDao.getForumMessage(rs.getInt("message_id"));
                forumMessageList.add(forumMessage);
            }
        } catch (SQLException sqle) {
            log.error("Ошибка чтения списка сообщений" , sqle);
            sqle.printStackTrace();
        } finally {
            try {
                connection.close();
                pstmt.close();
            } catch (SQLException sqle) {
                log.error("Ошибка в закрытии соединения",sqle);
                sqle.printStackTrace();
            }
        }
        return forumMessageList;
    }

    public List<Tag> getTags(Topic topic) {
        List<Tag> tagList = new ArrayList<Tag>();
        Tag tag = null;
        String sql = "SELECT * FROM tags WHERE tag_topic_id=?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, topic.getTopicId());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                tag = new Tag();
                tag.setTagName(rs.getString("tag_name"));
                tagList.add(tag);
            }
        } catch (SQLException sqle) {
            log.error("Ошибка чтения списка тегов для темы " + topic.getTopicName(), sqle);
            sqle.printStackTrace();
        } finally {
            try {
                connection.close();
                pstmt.close();
            } catch (SQLException sqle) {
                log.error("Ошибка в закрытии соединения",sqle);
                sqle.printStackTrace();
            }
        }
        return tagList;
    }
}

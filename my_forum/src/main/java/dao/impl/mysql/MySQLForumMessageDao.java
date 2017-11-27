package dao.impl.mysql;

import org.apache.log4j.Logger;
import dao.interfaces.ForumMessageDao;
import pojo.ForumMessage;
import pojo.Topic;
import pojo.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Класс, реализующий интерфейс ForumMessageDao.
 * @see dao.interfaces.ForumMessageDao
 */
public class MySQLForumMessageDao extends MySQLDao implements ForumMessageDao {

    Logger log = Logger.getLogger(MySQLForumMessageDao.class.getName());
    static MySQLUserDao userDao = new MySQLUserDao();
    static MySQLTopicsDao topicsDao = new MySQLTopicsDao();
    static SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");

    public void createForumMessage(ForumMessage forumMessage) {
        String sql = "INSERT INTO forum_messages (message_author_id, message_text, message_topic_id) " +
                "VALUES(?,?,?);";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, forumMessage.getMessageAuthor().getUserId());
            pstmt.setString(2, forumMessage.getMessageText());
            pstmt.setInt(3, forumMessage.getMessageTopic().getTopicId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в изменении конфигурации сайта", sqle);
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

    public ForumMessage getForumMessage(int forumMessageId) {
        String sql = "SELECT * FROM forum_messages WHERE message_id = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        ForumMessage forumMessage = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, forumMessageId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                forumMessage = new ForumMessage();
                forumMessage.setMessageId(rs.getInt("message_id"));
                forumMessage.setMessageAuthor(userDao.getUserForNotif(rs.getInt("message_author_id")));
                forumMessage.setMessageText(rs.getString("message_text"));
                forumMessage.setMessageDate(rs.getDate("message_datetime"));
                forumMessage.setMessageEditDate(rs.getDate("message_edit_datetime"));
                forumMessage.setMessageTopic(topicsDao.getTopicById(rs.getInt("message_topic_id")));
                forumMessage.setMessageTime(localDateFormat.format(forumMessage.getMessageDate()));
                forumMessage.setMessageEditTime(localDateFormat.format(forumMessage.getMessageEditDate()));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в изменении конфигурации сайта", sqle);
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
        return forumMessage;
    }

    public void updateForumMessage(ForumMessage forumMessage) {
        String sql = "UPDATE forum_messages SET message_text=? WHERE message_id = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, forumMessage.getMessageText());
            pstmt.setInt(2, forumMessage.getMessageId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в изменении сообщения " + forumMessage.getMessageId() + " в теме " + forumMessage.getMessageTopic().getTopicName(), sqle);
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

    public void like(ForumMessage message, User user) {
        String sql = "UPDATE forum_messages SET message_rating=? WHERE message_id = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, message.getMessageRating() + 1);
            pstmt.setInt(2, message.getMessageId());
            addLikedUser(message, user);
            userDao.updateRating(message.getMessageAuthor());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в изменении рейтинга сообщения " + message.getMessageId() + " в теме " + message.getMessageTopic().getTopicName(), sqle);
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

    public void unlike(ForumMessage message, User user) {
        String sql = "UPDATE forum_messages SET message_rating=? WHERE message_id = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, message.getMessageRating() - 1);
            pstmt.setInt(2, message.getMessageId());
            deleteLikedUser(message, user);
            userDao.minusRating(message.getMessageAuthor());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в изменении рейтинга сообщения " + message.getMessageId() + " в теме " + message.getMessageTopic().getTopicName(), sqle);
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

    private void addLikedUser(ForumMessage message, User user) {
        String sql = "INSERT INTO liked_message (user_id,message_id) VALUES(?,?);";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, message.getMessageId());
            pstmt.setInt(2, user.getUserId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в изменении рейтинга сообщения " + message.getMessageId() + " в теме " + message.getMessageTopic().getTopicName(), sqle);
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

    private void deleteLikedUser(ForumMessage message, User user) {
        String sql = "DELETE FROM liked_message WHERE message_id = ? AND user_id = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, message.getMessageId());
            pstmt.setInt(2, user.getUserId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в изменении рейтинга сообщения " + message.getMessageId() + " в теме " + message.getMessageTopic().getTopicName(), sqle);
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

    public void deleteForumMessage(ForumMessage forumMessage) {
        String sql = "DELETE FROM forum_messages WHERE message_id=?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, forumMessage.getMessageId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в изменении удалении сообщения " + forumMessage.getMessageId() +
                    " в теме " + forumMessage.getMessageTopic().getTopicName(), sqle);
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

    public void deleteAllForumMessage(User user, Topic topic) {
        String sql = "DELETE FROM forum_messages WHERE message_author_id=? AND message_topic_id = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getUserId());
            pstmt.setInt(2, topic.getTopicId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в удалении сообщений пользователя " +
                    user.getUsername() + " в теме " + topic.getTopicName(), sqle);
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

    public ForumMessage getMessageWithoutAuthor(int messageId) {
        String sql = "SELECT * FROM forum_messages WHERE message_id = ?";
        ForumMessage message = null;
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, messageId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                message = new ForumMessage();
                message.setMessageId(rs.getInt("message_id"));
                message.setMessageText(rs.getString("message_text"));
                message.setMessageDate(rs.getDate("message_datetime"));
                message.setMessageEditDate(rs.getDate("message_edit_datetime"));
                message.setMessageTopic(topicsDao.getTopicById(rs.getInt("message_topic_id")));
                message.setMessageTime(localDateFormat.format(message.getMessageDate()));
                message.setMessageEditTime(localDateFormat.format(message.getMessageEditDate()));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в чтении сообщения " +
                    message.getMessageText(), sqle);
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
        return message;
    }
}

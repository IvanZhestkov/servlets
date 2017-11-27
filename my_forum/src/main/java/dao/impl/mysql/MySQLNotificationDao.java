package dao.impl.mysql;

import org.apache.log4j.Logger;
import dao.DaoFactory;
import dao.interfaces.*;
import pojo.ForumMessage;
import pojo.Notification;
import pojo.Topic;
import pojo.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий интерфейс NotificationDao
 * @see dao.interfaces.NotificationDao
 */
public class MySQLNotificationDao extends MySQLDao implements NotificationDao {

    static Logger log = Logger.getLogger(MySQLNotificationDao.class.getName());
    static UserDao userDao = DaoFactory.getDaoFactory(1).getUserDao();
    static TopicDao topicDao = DaoFactory.getDaoFactory(1).getTopicDao();
    static ForumMessageDao messageDao = DaoFactory.getDaoFactory(1).getForumMessageDao();

    public void feed(User user, Topic topic) {
        String sql = "INSERT INTO feed_topics (topic_id, user_id) " +
                "VALUES(?,?);";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, topic.getTopicId());
            pstmt.setInt(2, user.getUserId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в подписке пользователя "+user.getUsername()+" на тему "+topic.getTopicName(), sqle);
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

    public void unfeed(User user, Topic topic) {
        String sql = "DELETE FROM feed_topics WHERE topic_id=? AND user_id=?";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, topic.getTopicId());
            pstmt.setInt(2, user.getUserId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в отписке пользователя "+user.getUsername()+" от темы "+topic.getTopicName(), sqle);
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

    public void createNotification(User user, Topic topic, ForumMessage message) {
        String sql = "INSERT INTO notifications (user_id,topic_id,message_id) VALUES(?,?,?);";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getUserId());
            pstmt.setInt(2, topic.getTopicId());
            pstmt.setInt(3, message.getMessageId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в создании уведомления для пользователя "+user.getUsername(), sqle);
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

    public List<Notification> getNotifications(User user) {
        String sql = "SELECT * FROM notifications WHERE user_id=?;";
        log.trace("Открытие соединения");
        List<Notification> notificationList = new ArrayList<Notification>();
        Notification temp = null;
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getUserId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                temp = new Notification();
                temp.setId(rs.getInt("id"));
                temp.setUser(userDao.getUserForNotif(rs.getInt("user_id")));
                temp.setTopic(topicDao.getTopicById(rs.getInt("topic_id")));
                temp.setMessage(messageDao.getForumMessage(rs.getInt("message_id")));
                notificationList.add(temp);
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в извлечении списка уведомлений для пользователя "+user.getUsername(), sqle);
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
        return notificationList;
    }

    public boolean isFeeded(User user, Topic topic) {
        String sql = "SELECT * FROM feed_topics WHERE user_id=? AND topic_id=?;";
        log.trace("Открытие соединения");
        boolean isFeed = false;
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getUserId());
            pstmt.setInt(2, topic.getTopicId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                isFeed = true;
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в проверке на подписку пользователя "+user.getUsername(), sqle);
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
        return isFeed;
    }

    public List<User> getUsers(Topic topic) {
        String sql = "SELECT * FROM feed_topics WHERE topic_id=?;";
        log.trace("Открытие соединения");
        List<User> userList = new ArrayList<User>();
        User user = null;
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, topic.getTopicId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                user = userDao.getUserForNotif(rs.getInt("user_id"));
                userList.add(user);
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в извлечении списка уведомлений для пользователя "+user.getUsername(), sqle);
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
        return userList;
    }
}

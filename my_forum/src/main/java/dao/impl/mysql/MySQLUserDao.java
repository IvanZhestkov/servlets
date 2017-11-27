package dao.impl.mysql;

import org.apache.log4j.Logger;
import dao.DaoFactory;
import dao.interfaces.ForumMessageDao;
import dao.interfaces.NotificationDao;
import dao.interfaces.UserDao;
import pojo.ForumMessage;
import pojo.Topic;
import pojo.user.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий UserDao
 */
public class MySQLUserDao extends MySQLDao implements UserDao {
    Logger log = Logger.getLogger(MySQLUserDao.class.getName());

    static MySQLTopicsDao topicsDao = new MySQLTopicsDao();
    static NotificationDao notificationDao = DaoFactory.getDaoFactory(1).getNotificationDao();
    static ForumMessageDao messageDao = DaoFactory.getDaoFactory(1).getForumMessageDao();

    public void createUser(User user) {
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement("INSERT INTO users "
                    + "(user_name, user_password, user_email, user_fname, user_lname, user_info, user_role)"
                    + "VALUES(?,?,?,?,?,?,?)");
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordMd5());
            pstmt.setString(3, user.getUserEmail());
            pstmt.setString(4, user.getUserFname());
            pstmt.setString(5, user.getUserLname());
            pstmt.setString(6, user.getUserInfo());
            pstmt.setString(7, user.getUserRole());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в создании пользователя " +
                    user.getUsername(), sqle);
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

    public void addRememberUuid(User user, String uuid) {
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement("UPDATE users SET remember=? WHERE user_id =?;");
            pstmt.setString(1, uuid);
            pstmt.setInt(2, user.getUserId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в создании rememberme записи для пользователя " +
                    user.getUsername(), sqle);
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

    public User getUserByTokenRemember(String token) {
        String sql = "SELECT user_id " +
                "FROM users " +
                "WHERE remember = ?";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        User user = null;
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, token);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                user = getUser(rs.getInt("user_id"));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в получении пользователя " +
                    user.getUsername(), sqle);
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
        return user;
    }

    public void deleteRememberUuid(int userId) {
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement("UPDATE users SET remember=? WHERE user_id =?;");
            pstmt.setString(1, null);
            pstmt.setInt(2, userId);
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в удалении записи rememberme пользователя " +
                    getUser(userId).getUsername(), sqle);
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

    public User getUserForNotif(int userId) {
        String sql = "SELECT * " +
                "FROM users " +
                "WHERE user_id = ?";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        User user = null;
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                UserTypes usrt = UserTypes.valueOf(rs.getString("user_role"));
                switch (usrt) {
                    case ADMIN:
                        user = new Admin();
                        break;
                    case MODERATOR:
                        user = new Moderator();
                        break;
                    case MEMBER:
                        user = new Member();
                        break;
                }
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("user_name"));
                user.setPasswordMd5(rs.getString("user_password"));
                user.setUserEmail(rs.getString("user_email"));
                user.setUserFname(rs.getString("user_fname"));
                user.setUserLname(rs.getString("user_lname"));
                user.setUserInfo(rs.getString("user_info"));
                user.setUserRating(rs.getInt("user_rating"));
                user.setAnswersNumber(getAnswersNumber(user));
                user.setTopicsNumber(getTopicsNumber(user));
                user.setDeleteDate(rs.getDate("delete_date"));
                user.setActive(rs.getBoolean("user_active"));
                user.setConfirmed(rs.getBoolean("user_confirmed"));
                user.setRegisterDate(rs.getDate("user_register_datetime"));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в получении пользователя " +
                    user.getUsername(), sqle);
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
        return user;
    }

    public User getUser(int userId) {
        String sql = "SELECT * " +
                "FROM users " +
                "WHERE user_id = ?";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        User user = null;
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                UserTypes usrt = UserTypes.valueOf(rs.getString("user_role"));
                switch (usrt) {
                    case ADMIN:
                        user = new Admin();
                        break;
                    case MODERATOR:
                        user = new Moderator();
                        break;
                    case MEMBER:
                        user = new Member();
                        break;
                }
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("user_name"));
                user.setPasswordMd5(rs.getString("user_password"));
                user.setUserEmail(rs.getString("user_email"));
                user.setUserFname(rs.getString("user_fname"));
                user.setUserLname(rs.getString("user_lname"));
                user.setUserInfo(rs.getString("user_info"));
                user.setUserRating(rs.getInt("user_rating"));
                user.setAnswersNumber(getAnswersNumber(user));
                user.setTopicsNumber(getTopicsNumber(user));
                user.setDeleteDate(rs.getDate("delete_date"));
                user.setActive(rs.getBoolean("user_active"));
                user.setConfirmed(rs.getBoolean("user_confirmed"));
                user.setRegisterDate(rs.getDate("user_register_datetime"));
                user.setNotificationList(notificationDao.getNotifications(user));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в получении пользователя " +
                    user.getUsername(), sqle);
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
        return user;
    }

    public User getUserByName(String username) {
        String sql = "SELECT * FROM users WHERE user_name = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        User user = null;
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                user = getUser(rs.getInt("user_id"));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в получении пользователя " +
                    user.getUsername(), sqle);
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
        return user;
    }

    public User getUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE user_name = ? AND user_password = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        User user = null;
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                user = getUser(rs.getInt("user_id"));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в получении пользователя " +
                    user.getUsername(), sqle);
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
        return user;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE user_email = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        User user = null;
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,email);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                user = getUser(rs.getInt("user_id"));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в получении пользователя " +
                    user.getUsername(), sqle);
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
        return user;
    }

    public List<Topic> getTopics(User user, int number) {
        String sql = "SELECT topic_id " +
                "FROM topics " +
                "WHERE topic_author_id = ? " +
                "ORDER BY topic_create_datetime DESC " +
                "LIMIT 0,"+number+";";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        List<Topic> topicList = new ArrayList<Topic>();
        Topic temp = null;
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,user.getUserId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                temp = topicsDao.getTopicById(rs.getInt("topic_id"));
                topicList.add(temp);
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в получении последних тем пользователя " +
                    user.getUsername(), sqle);
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

    public void updateUser(User user) {
        String sql = "UPDATE users SET " +
                "user_name = ?," +
                "user_password = ?," +
                "user_email = ?," +
                "user_fname = ?," +
                "user_lname = ?," +
                "user_info = ?," +
                "user_role = ?," +
                "user_rating = ?," +
                "user_active = ?," +
                "user_confirmed = ? " +
                "WHERE user_id=?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordMd5());
            pstmt.setString(3, user.getUserEmail());
            pstmt.setString(4, user.getUserFname());
            pstmt.setString(5, user.getUserLname());
            pstmt.setString(6, user.getUserInfo());
            pstmt.setString(7, user.getUserRole());
            pstmt.setInt(8, user.getUserRating());
            pstmt.setBoolean(9, user.isActive());
            pstmt.setBoolean(10, user.isConfirmed());
            pstmt.setInt(11, user.getUserId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в редактировании пользователя " +
                    user.getUsername(), sqle);
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

    public void updateRating(User user) {
        String sql = "UPDATE users SET user_rating = ? WHERE user_id = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getUserRating() + 1);
            pstmt.setInt(2, user.getUserId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в изменении рейтинга пользователя " + user.getUsername(), sqle);
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

    public void minusRating(User user) {
        String sql = "UPDATE users SET user_rating = ? WHERE user_id = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getUserRating() - 1);
            pstmt.setInt(2, user.getUserId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в изменении рейтинга пользователя " + user.getUsername(), sqle);
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

    public void deleteUser(User user) {
        String sql = "DELETE FROM users WHERE user_id=?";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getUserId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в удалении пользователя " +
                    user.getUsername(), sqle);
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

    public List<User> getAllUsers() throws SQLException {
        List<User> usersList = new ArrayList<User>();
        User user = null;
        String sql = "SELECT * FROM users;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            UserTypes usrt = null;
            while(rs.next()) {
                usrt = UserTypes.valueOf(rs.getString("user_role"));
                switch (usrt) {
                    case ADMIN:
                        user = new Admin();
                        break;
                    case MODERATOR:
                        user = new Moderator();
                        break;
                    case MEMBER:
                        user = new Member();
                        break;
                }
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("user_name"));
                user.setPasswordMd5(rs.getString("user_password"));
                user.setUserEmail(rs.getString("user_email"));
                user.setUserFname(rs.getString("user_fname"));
                user.setUserLname(rs.getString("user_lname"));
                user.setUserInfo(rs.getString("user_info"));
                user.setUserRating(rs.getInt("user_rating"));
                user.setAnswersNumber(getAnswersNumber(user));
                user.setTopicsNumber(getTopicsNumber(user));
                user.setLastTopic(getLastTopicByUser(user));
                user.setDeleteDate(rs.getDate("delete_date"));
                user.setActive(rs.getBoolean("user_active"));
                user.setConfirmed(rs.getBoolean("user_confirmed"));
                usersList.add(user);
            }
        } catch (SQLException sqle) {
            log.error("Ошибка чтения списка пользователей ", sqle);
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
        return usersList;
    }

    public int getAnswersNumber(User user) {
        String sql = "SELECT COUNT(message_id) 'answers_number' FROM forum_messages WHERE message_author_id = ?";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        int answersNumber = 0;
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getUserId());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                answersNumber = rs.getInt("answers_number");
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в получении количества сообщений пользователя " +
                    user.getUsername(), sqle);
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
        return answersNumber;
    }

    public int getTopicsNumber(User user) {
        String sql = "SELECT COUNT(topic_id) 'topics_number' FROM topics WHERE topic_author_id = ?";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        int topicsNumber = 0;
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getUserId());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                topicsNumber = rs.getInt("topics_number");
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в получении количества тем пользователя " +
                    user.getUsername(), sqle);
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
        return topicsNumber;
    }

    public Topic getLastTopicByUser(User user) {
        String sql = "SELECT topic_id " +
                "FROM topics t " +
                "WHERE topic_author_id = ? AND topic_create_datetime = (SELECT MAX(topic_create_datetime) FROM topics t2 WHERE t2.topic_author_id = t.topic_author_id)";
        Topic lastTopic = null;
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getUserId());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                lastTopic = topicsDao.getTopicByIdWithoutAuthor(rs.getInt("topic_id"));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в получении последней темы пользователя " +
                    user.getUsername(), sqle);
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
        return lastTopic;
    }

    public ForumMessage getLastMessage(User user) {
        String sql = "SELECT message_id " +
                "FROM forum_messages t " +
                "WHERE message_author_id = ? AND message_datetime = (SELECT MAX(message_datetime) FROM forum_messages t2 WHERE t2.message_author_id = t.message_author_id)";
        ForumMessage message = null;
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getUserId());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                message = messageDao.getMessageWithoutAuthor(rs.getInt("message_id"));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в получении последней темы пользователя " +
                    user.getUsername(), sqle);
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
        return message;
    }
}

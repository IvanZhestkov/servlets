package dao.impl.mysql;

import org.apache.log4j.Logger;
import dao.interfaces.StatsDao;
import pojo.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс, реализализующий интерфейс StatsDao
 * @see dao.interfaces.StatsDao
 */
public class MySQLStatsDao extends MySQLDao implements StatsDao {

    Logger log = Logger.getLogger(MySQLStatsDao.class.getName());
    static MySQLUserDao userDao = new MySQLUserDao();

    public int getForumMessagesNumber() {
        String sql = "SELECT COUNT(*) 'messagesNumber' " +
                "FROM forum_messages;";
        int messagesNumber = 0;
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                messagesNumber = rs.getInt("messagesNumber");
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в подсчете количества сообщений на сайте", sqle);
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
        return messagesNumber;
    }

    public int getTopicsNumber() {
        String sql = "SELECT COUNT(*) 'topicsNumber' " +
                "FROM topics;";
        int topicsNumber = 0;
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                topicsNumber = rs.getInt("topicsNumber");
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в подсчете количества тем на сайте", sqle);
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
        return topicsNumber;
    }

    public int getUsersNumber() {
        String sql = "SELECT COUNT(*) 'usersNumber' " +
                "FROM users;";
        int usersNumber = 0;
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                usersNumber = rs.getInt("usersNumber");
            }
        } catch (SQLException sqle) {
            log.error("Ошибка при подсчете количества пользователей на сайте", sqle);
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
        return usersNumber;
    }

    public User getLastUser() {
        String sql = "SELECT user_id " +
                "FROM users " +
                "WHERE user_register_datetime = (SELECT MAX(user_register_datetime) FROM users);";
        User lastUser = null;
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                lastUser = userDao.getUser(rs.getInt("user_id"));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в получении последнего, зарегистрировавшегося пользователя", sqle);
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
        return lastUser;
    }
}

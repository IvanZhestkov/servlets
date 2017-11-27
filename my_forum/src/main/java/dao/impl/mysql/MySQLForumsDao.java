package dao.impl.mysql;

import org.apache.log4j.Logger;
import dao.interfaces.ForumsDao;
import pojo.Forum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с форумами из базы данных
 */
public class MySQLForumsDao extends MySQLDao implements ForumsDao {

    Logger log = Logger.getLogger(MySQLForumsDao.class.getName());
    static MySQLSectionDao sectionDao = new MySQLSectionDao();

    public void createForum(Forum forum) {
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement("INSERT INTO forums "
            + "(forum_name, forum_desc)"
            + "VALUES(?,?)");
            pstmt.setString(1, forum.getForumName());
            pstmt.setString(2, forum.getForumDescription());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в создании форума", sqle);
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

    public Forum getForum(int forumId) {
        String sql = "SELECT * FROM forums WHERE forum_id = ?;";
        Forum forum = null;
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, forumId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                forum = new Forum();
                forum.setForumId(forumId);
                forum.setForumName(rs.getString("forum_name"));
                forum.setForumDescription(rs.getString("forum_desc"));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в prepared statement", sqle);
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
        return forum;
    }

    public List<Forum> getAllForums() {
        List<Forum> forumList = new ArrayList<Forum>();
        Forum forum = null;
        String sql = "SELECT * FROM forums;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Чтение списка форумов");
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                forum = getForum(rs.getInt("forum_id"));
                forumList.add(forum);
            }
        } catch (SQLException sqle) {
            log.error("Ошибка чтения списка форумов", sqle);
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
        return forumList;
    }

    public void deleteForum(Forum forum) {
        String sql = "DELETE FROM forums WHERE forum_id = ?";
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, forum.getForumId());

            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в prepared statement", sqle);
            sqle.printStackTrace();
        }
    }

    public void updateForum(Forum forum) {
        String sql = "UPDATE forums SET forum_name = ?, forum_desc = ?" +
                "WHERE forum_id = ?";
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, forum.getForumName());
            pstmt.setString(2, forum.getForumDescription());
            pstmt.setInt(3, forum.getForumId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка с prepared statement", sqle);
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
    }

}

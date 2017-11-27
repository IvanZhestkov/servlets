package dao.impl.mysql;

import dao.DaoFactory;
import dao.interfaces.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Класс, реализующий абстрактный класс DaoFactory
 * @see DaoFactory
 */
public class MySQLDaoFactory extends DaoFactory {

    public static final String JNDI_MYSQL_RESOURCE = "java:comp/env/jdbc/blaboard";

    public Connection createConnection() {

        Context cntx = null;

        try {
            cntx = new InitialContext();
            return ((DataSource) cntx.lookup(JNDI_MYSQL_RESOURCE)).getConnection();
        } catch (NamingException ne) {
            ne.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }

    public Connection getConnection() throws SQLException {
        return createConnection();
    }

    public ForumsDao getForumsDao() {
        return new MySQLForumsDao();
    }

    public SectionDao getSectionDao() {
        return new MySQLSectionDao();
    }

    public TopicDao getTopicDao() {
        return new MySQLTopicsDao();
    }

    public ForumMessageDao getForumMessageDao() {
        return new MySQLForumMessageDao();
    }

    public UserDao getUserDao() {
        return new MySQLUserDao();
    }

    public TokenDao getTokenDao() {
        return new MySQLTokenDao();
    }

    public SearchDao getSearchDao() {
        return new MySQLSearchDao();
    }

    public StatsDao getStatsDao() {
        return new MySQLStatsDao();
    }

    public TagDao getTagDao() {
        return new MySQLTagDao();
    }

    public NotificationDao getNotificationDao() {
        return new MySQLNotificationDao();
    }
}

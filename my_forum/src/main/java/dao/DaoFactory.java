package dao;

import dao.impl.mysql.MySQLDaoFactory;
import dao.interfaces.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Фабрика объектов для работы с базой
 */
public abstract class DaoFactory {

    public static final int MYSQL = 1;

    public static DaoFactory getDaoFactory(int DBMSIndex) {
        switch (DBMSIndex) {
            case MYSQL:
                return new MySQLDaoFactory();
            default:
                return null;
        }
    }

    public abstract Connection getConnection() throws SQLException;

    public abstract ForumsDao getForumsDao();

    public abstract SectionDao getSectionDao();

    public abstract SearchDao getSearchDao();

    public abstract StatsDao getStatsDao();

    public abstract TagDao getTagDao();

    public abstract TopicDao getTopicDao();

    public abstract ForumMessageDao getForumMessageDao();

    public abstract UserDao getUserDao();

    public abstract TokenDao getTokenDao();

    public abstract NotificationDao getNotificationDao();

}

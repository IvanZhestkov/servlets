package pojo;

import dao.impl.mysql.MySQLStatsDao;

/**
 * Класс, описывающий объект статистики сайта
 */
public class Stats {

    static MySQLStatsDao statsDao = new MySQLStatsDao();

    public Stats() {
        this.forumMessagesNumber = statsDao.getForumMessagesNumber();
        this.topicsNumber = statsDao.getTopicsNumber();
        this.usersNumber = statsDao.getUsersNumber();
    }

    private int forumMessagesNumber;
    private int topicsNumber;
    private int usersNumber;

    public void setForumMessagesNumber(int forumMessagesNumber) {
        this.forumMessagesNumber = forumMessagesNumber;
    }

    public void setTopicsNumber(int topicsNumber) {
        this.topicsNumber = topicsNumber;
    }

    public void setUsersNumber(int usersNumber) {
        this.usersNumber = usersNumber;
    }

    public int getForumMessagesNumber() {
        return forumMessagesNumber;
    }

    public int getTopicsNumber() {
        return topicsNumber;
    }

    public int getUsersNumber() {
        return usersNumber;
    }

}

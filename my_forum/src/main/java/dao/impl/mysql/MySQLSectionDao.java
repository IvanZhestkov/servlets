package dao.impl.mysql;

import org.apache.log4j.Logger;
import dao.interfaces.SectionDao;
import pojo.Forum;
import pojo.Section;
import pojo.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализизующий интерфейс SectionDao
 *
 * @see SectionDao
 */
public class MySQLSectionDao extends MySQLDao implements SectionDao {

    Logger log = Logger.getLogger(MySQLForumsDao.class.getName());
    static MySQLTopicsDao topicsDao = new MySQLTopicsDao();
    static MySQLForumsDao forumDao = new MySQLForumsDao();
    static MySQLForumMessageDao forumMessageDao = new MySQLForumMessageDao();


    public void createSection(Section section) {
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement("INSERT INTO sections "
                    + "(section_name, section_desc, section_forum_id, section_parent_id)"
                    + "VALUES(?,?,?,?)");
            pstmt.setString(1, section.getSectionName());
            pstmt.setString(2, section.getSectionDescription());
            pstmt.setInt(3, section.getForum().getForumId());
            if (section.getParentSection().getSectionId() != -1) {
                pstmt.setInt(4, section.getParentSection().getSectionId());
                createSubsection(section);
            }
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в создании раздела " +
                    section.getSectionName(), sqle);
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

    public void createSubsection(Section section) {
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement("INSERT INTO subsections "
                    + "(section_child_id, section_parent_id)"
                    + "VALUES(?,?)");
            pstmt.setInt(1, section.getSectionId());
            pstmt.setInt(2, section.getParentSection().getSectionId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в создании подраздела " +
                    section.getSectionName(), sqle);
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

    public void updateSubsection(Section section) {
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            log.trace("Создание prepared statement");
            pstmt = connection.prepareStatement("UPDATE subsections SET "
                    + "section_child_id = ?, section_parent_id = ?");
            pstmt.setInt(1, section.getSectionId());
            pstmt.setInt(2, section.getParentSection().getSectionId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в создании подраздела " +
                    section.getSectionName(), sqle);
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

    public Section getSection(int sectionId) {
        String sql = "SELECT * FROM sections WHERE section_id = ?";
        Section section = null;
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, sectionId);
            ResultSet rs = pstmt.executeQuery();
            section = new Section();
            if (rs.next()) {
                section.setSectionId(sectionId);
                section.setSectionName(rs.getString("section_name"));
                section.setSectionDescription(rs.getString("section_desc"));
                section.setForum(forumDao.getForum(rs.getInt("section_forum_id")));
                if (rs.getInt("section_parent_id") != -1) {
                    section.setParentSection(new Section());
                    section.getParentSection().setSectionId(rs.getInt("section_parent_id"));
                }
                section.setSubsections(getAllSubsections(section));
                section.setSectionTopicNum(getCountOfMessages(section));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в чтении раздела " +
                    section.getSectionName(), sqle);
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
        return section;
    }

    public Topic getLastTopic(Section section) {
        String sql = "SELECT topic_id FROM topics t JOIN sections s ON t.topic_section_id = s.section_id " +
                "WHERE section_id = ? " +
                "ORDER BY topic_create_datetime DESC " +
                "LIMIT 1;";
        Topic topic = null;
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, section.getSectionId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                topic = topicsDao.getTopicById(rs.getInt("topic_id"));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в чтении последнего сообщения раздела " +
                    section.getSectionName(), sqle);
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
        return topic;
    }

    public long getCountOfMessages(Section section) {
        String sql = "SELECT COUNT(message_id) 'count' " +
                "FROM forum_messages fm JOIN topics t ON fm.message_topic_id = t.topic_id JOIN sections s ON t.topic_section_id = s.section_id " +
                "WHERE s.section_id = ? OR s.section_parent_id = ?";
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, section.getSectionId());
            pstmt.setInt(2, section.getSectionId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                section.setSectionTopicNum(rs.getInt("count"));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в чтении последнего сообщения раздела " +
                    section.getSectionName(), sqle);
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
        return section.getSectionTopicNum();
    }

    public void updateSection(Section section) {
        String sql = "UPDATE sections SET section_name = ?, section_forum_id = ?, section_desc = ?, section_parent_id = ?" +
                "WHERE section_id = ?";
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, section.getSectionName());
            pstmt.setInt(2, section.getForum().getForumId());
            pstmt.setString(3, section.getSectionDescription());
            if (section.getParentSection().getSectionId() != -1) {
                pstmt.setInt(4, section.getParentSection().getSectionId());
                updateSubsection(section);
            }
            pstmt.setInt(5, section.getSectionId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в изменении раздела " +
                    section.getSectionName() + " форума " +
                    section.getForum().getForumName(), sqle);
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

    public void deleteSection(Section section) {
        String sql = "DELETE FROM sections " +
                "WHERE section_id = ?";
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, section.getSectionId());
            pstmt.execute();
        } catch (SQLException sqle) {
            log.error("Ошибка в удалении раздела " + section.getSectionName()
                    + " из форума" + section.getForum().getForumName(), sqle);
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

    public List<Section> getAllSections(Forum forum) throws SQLException {
        List<Section> sectionList = new ArrayList<Section>();
        Section section = null;
        String sql = "SELECT * FROM sections WHERE section_forum_id=? AND section_parent_id = -1;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, forum.getForumId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                section = getSection(rs.getInt("section_id"));
                sectionList.add(section);
            }
        } catch (SQLException sqle) {
            log.error("Ошибка чтения списка разделов форума " + forum.getForumName(), sqle);
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
        return sectionList;
    }

    public List<Section> getAllSubsections(Section section) throws SQLException {
        List<Section> sectionList = new ArrayList<Section>();
        Section subsection;
        String sql = "SELECT * FROM sections s JOIN subsections sb ON sb.section_child_id = s.section_id " +
                "WHERE s.section_parent_id = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, section.getSectionId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                subsection = getSection(rs.getInt("section_id"));
                if(getLastTopic(subsection) != null) {
                    subsection.setLastTopic(getLastTopic(subsection));
                }
                sectionList.add(subsection);
            }
        } catch (SQLException sqle) {
            log.error("Ошибка чтения списка подразделов раздела " + section.getSectionName(), sqle);
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
        return sectionList;
    }

    public List<Topic> getAllTopics(Section section) {
        List<Topic> topicList = new ArrayList<Topic>();
        Topic topic = null;
        String sql = "SELECT * FROM topics WHERE topic_section_id = ?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, section.getSectionId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                topic = topicsDao.getTopicById(rs.getInt("topic_id"));
                topicList.add(topic);
            }
        } catch (SQLException sqle) {
            log.error("Ошибка чтения списка подразделов раздела " + section.getSectionName(), sqle);
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

    public List<Topic> getAllTopicsPaging(Section section, int page, int countOfTopics) {
        List<Topic> topicList = new ArrayList<Topic>();
        Topic topic = null;
        String sql = "SELECT * " +
                "FROM topics " +
                "WHERE topic_section_id = ? " +
                "ORDER BY topic_create_datetime DESC " +
                "LIMIT " + page + "," + countOfTopics;
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, section.getSectionId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                topic = topicsDao.getTopicById(rs.getInt("topic_id"));
                topicList.add(topic);
            }
        } catch (SQLException sqle) {
            log.error("Ошибка чтения списка подразделов раздела " + section.getSectionName(), sqle);
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

    public int getPagesNum(Section section, int numberOfTopicsOnPage) {
        int topicsNum = 0;
        String sql = "SELECT COUNT(topic_id) FROM topics WHERE topic_section_id=?;";
        log.trace("Открытие соединения");
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, section.getSectionId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                topicsNum = rs.getInt(1);
            }
        } catch (SQLException sqle) {
            log.error("Ошибка чтения списка подразделов раздела " + section.getSectionName(), sqle);
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
        return (int) Math.ceil(Double.valueOf(topicsNum / numberOfTopicsOnPage));
    }
}

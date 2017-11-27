package dao.impl.mysql;

import org.apache.log4j.Logger;
import dao.interfaces.SearchDao;
import pojo.Section;
import pojo.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий SearchDao
 * @see dao.interfaces.SearchDao
 */
public class MySQLSearchDao extends MySQLDao implements SearchDao {

    static Logger log = Logger.getLogger(MySQLSearchDao.class.getName());
    static MySQLSectionDao sectionDao = new MySQLSectionDao();
    static MySQLTopicsDao topicsDao = new MySQLTopicsDao();

    public List<Section> searchSection(String key) {
        String sql = "SELECT * FROM sections WHERE section_name LIKE '%"+key+"%'";
        Section section = null;
        List<Section> sectionList = new ArrayList<Section>();
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                section = sectionDao.getSection(rs.getInt("section_id"));
                sectionList.add(section);
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
        return sectionList;
    }

    public List<Topic> searchTopic(String key) {
        String sql = "SELECT * FROM topics WHERE topic_name LIKE '%"+key+"%'";
        Topic topic = null;
        List<Topic> topicList = new ArrayList<Topic>();
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                topic = topicsDao.getTopicById(rs.getInt("topic_id"));
                topicList.add(topic);
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
        return topicList;
    }
}

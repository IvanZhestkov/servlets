package web;

import org.apache.log4j.Logger;
import dao.DaoFactory;
import dao.impl.mysql.*;
import dao.interfaces.NotificationDao;
import pojo.*;
import pojo.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Сервлет, отвечающий за создание темы
 */
public class CreateTopicServlet extends HttpServlet {

    Logger log = Logger.getLogger(CreateTopicServlet.class.getName());
    static MySQLForumsDao forumsDao = new MySQLForumsDao();
    static MySQLSectionDao sectionDao = new MySQLSectionDao();
    static MySQLTopicsDao topicsDao = new MySQLTopicsDao();
    static MySQLTagDao tagDao = new MySQLTagDao();
    static MySQLUserDao userDao = new MySQLUserDao();
    static NotificationDao notificationDao = DaoFactory.getDaoFactory(1).getNotificationDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        List<Forum> forumList = null;
        User user = (User) req.getSession().getAttribute("user");

        try {
            forumList = forumsDao.getAllForums();
            Iterator<Forum> forumIterator = forumList.iterator();
            Forum temp = null;
            while (forumIterator.hasNext()) {
                temp = forumIterator.next();
                temp.setForumSections(sectionDao.getAllSections(temp));
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в чтении списка форумов", sqle);
        }
        req.setAttribute("forums", forumList);
        req.setAttribute("user", user);
        req.getServletContext().getRequestDispatcher("/create_topic.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        int sectionId = Integer.valueOf(req.getParameter("topic_section"));
        String topicName = req.getParameter("topic_name");
        String topicText = req.getParameter("editor_text");
        String topicTags = req.getParameter("topic_tags");
        if (!topicTags.equals("")) topicTags = req.getParameter("topic_tags").replaceAll("\\s+", "");
        User user = (User) req.getSession().getAttribute("user");
        Section section = sectionDao.getSection(sectionId);
        Topic topic = new Topic();
        topic.setTopicName(topicName);
        topic.setTopicSection(section);
        topic.setTopicText(topicText);
        topic.setTopicAuthor(user);
        List<Tag> tags = new ArrayList<Tag>();
        if (!topicTags.equals("")) {
            Tag temp = null;
            for (String tag : topicTags.split(",")) {
                temp = new Tag();
                temp.setTagName(tag);
                tags.add(temp);
            }
        }
        topic.setTopicTags(tags);
        topicsDao.createTopic(topic);
        notificationDao.feed(user, userDao.getLastTopicByUser(user));
        req.setAttribute("user", user);
        resp.sendRedirect("topic?id=" + userDao.getLastTopicByUser(user).getTopicId());
    }
}

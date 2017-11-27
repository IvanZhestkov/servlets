package web;

import org.apache.log4j.Logger;
import dao.DaoFactory;
import dao.interfaces.*;
import pojo.ForumMessage;
import pojo.Topic;
import pojo.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Сервлет, принимающий запросы для темы
 */
public class TopicServlet extends HttpServlet {

    static Logger log = Logger.getLogger(ForumsServlet.class.getName());
    static TopicDao topicsDao = DaoFactory.getDaoFactory(1).getTopicDao();
    static ForumMessageDao messageDao = DaoFactory.getDaoFactory(1).getForumMessageDao();
    static NotificationDao notificationDao = DaoFactory.getDaoFactory(1).getNotificationDao();
    static UserDao userDao = DaoFactory.getDaoFactory(1).getUserDao();


    public void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        int topicId = Integer.valueOf(req.getParameter("id"));
        User user = (User) req.getSession().getAttribute("user");
        Topic topic = topicsDao.getTopicById(topicId);
        if(topic == null) {
            req.setAttribute("error_code", 404);
            req.setAttribute("error_message", "Такой темы не существует. Возможно вы ошиблись или ввели какую-то ерунду.");
            req.getServletContext().getRequestDispatcher("/404.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("topic", topic);
        List<ForumMessage> forumMessageList = null;
        try {
            forumMessageList = topicsDao.getAllMessages(topic);
        } catch (SQLException sqle) {
            log.error("Ошибка в извлечении списка сообщений в теме "+topic.getTopicId(), sqle);
            sqle.printStackTrace();
        }
        req.setAttribute("forumMessages", forumMessageList);
        req.setAttribute("user", user);
        if(user != null) {
            req.setAttribute("isFeeded", notificationDao.isFeeded(user,topic));
        }
        req.getServletContext().getRequestDispatcher("/topic.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        int topicId = Integer.valueOf(req.getParameter("id"));
        User user = (User) req.getSession().getAttribute("user");
        Topic topic = topicsDao.getTopicById(topicId);
        if(req.getParameter("editor_text") != null) {
            String messageText = req.getParameter("editor_text");
            ForumMessage forumMessage = null;
            if (messageText != null) {
                forumMessage = new ForumMessage();
                forumMessage.setMessageAuthor(user);
                forumMessage.setMessageText(messageText);
                forumMessage.setMessageTopic(topic);
                messageDao.createForumMessage(forumMessage);
                Iterator<User> userIterator = notificationDao.getUsers(topic).iterator();
                while (userIterator.hasNext()) {
                    User temp = userIterator.next();
                    if(temp.getUserId() != user.getUserId()) {
                        notificationDao.createNotification(temp, topic, userDao.getLastMessage(user));
                    }
                }
            }
        }
        resp.sendRedirect("topic?id="+topicId);
    }
}

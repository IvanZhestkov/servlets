package web;

import dao.DaoFactory;
import dao.interfaces.NotificationDao;
import dao.interfaces.TopicDao;
import pojo.Topic;
import pojo.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/feed")
public class FeedTopicServlet extends HttpServlet {

    static NotificationDao notificationDao = DaoFactory.getDaoFactory(1).getNotificationDao();
    static TopicDao topicDao = DaoFactory.getDaoFactory(1).getTopicDao();

    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        int topicId = Integer.valueOf(req.getParameter("topic_id"));
        Topic topic = topicDao.getTopicById(topicId);
        notificationDao.feed(user, topic);
        resp.setHeader("REQUIRES_AUTH", "1");
        resp.sendRedirect("topic?id="+topicId);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }
}

package web;

import dao.DaoFactory;
import dao.interfaces.UserDao;
import pojo.Topic;
import pojo.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Сервлет, отвечающий за страницу профиля пользователя
 */
@WebServlet("/user")
public class ProfileServlet extends HttpServlet {

    static UserDao userDao = DaoFactory.getDaoFactory(1).getUserDao();

    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String username = req.getParameter("name");
        User user = userDao.getUserByName(username);
        if(user == null) {
            req.setAttribute("error_code", 404);
            req.setAttribute("error_message", "Такой пользователь не найден.");
            req.getServletContext().getRequestDispatcher("/404.jsp").forward(req, resp);
            return;
        } else {
            List<Topic> topicList = userDao.getTopics(user, 10);
            req.setAttribute("topics", topicList);
            req.setAttribute("cuser", user);
            req.setAttribute("alltopics", userDao.getTopicsNumber(user));
            req.setAttribute("allanswers", userDao.getAnswersNumber(user));
            req.setAttribute("user", (User) req.getSession().getAttribute("user"));
            req.getServletContext().getRequestDispatcher("/user.jsp").forward(req, resp);
        }
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

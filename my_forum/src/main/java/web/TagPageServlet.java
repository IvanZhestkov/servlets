package web;

import dao.impl.mysql.MySQLTagDao;
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
 * Сервлет, отвечающий за вывод тем с тегом
 */
@WebServlet("/tag")
public class TagPageServlet extends HttpServlet {

    static MySQLTagDao tagDao = new MySQLTagDao();

    public void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        User user = (User) req.getSession().getAttribute("user");
        String tagName = req.getParameter("name");
        List<Topic> topicList = tagDao.getTopics(tagName);
        req.setAttribute("user", user);
        req.setAttribute("tagName", tagName);
        req.setAttribute("topics", topicList);
        req.getServletContext().getRequestDispatcher("/tag.jsp").forward(req, resp);
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

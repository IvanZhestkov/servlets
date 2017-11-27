package web;

import dao.impl.mysql.MySQLSearchDao;
import pojo.Section;
import pojo.Topic;
import pojo.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Сервлет, отвечающий за обработку поискового запроса
 */
public class SearchServlet extends HttpServlet {

    static MySQLSearchDao searchDao = new MySQLSearchDao();

    public void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        User user = (User) req.getSession().getAttribute("user");
        String searchKey = req.getParameter("key");
        List<Section> sectionList = searchDao.searchSection(searchKey);
        List<Topic> topicList = searchDao.searchTopic(searchKey);
        req.setAttribute("s_sections", sectionList);
        req.setAttribute("s_topics", topicList);
        req.setAttribute("searchKey", searchKey);
        req.setAttribute("user", user);
        req.getServletContext().getRequestDispatcher("/search.jsp").forward(req, resp);
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

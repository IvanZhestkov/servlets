package web;

import org.apache.log4j.Logger;
import dao.impl.mysql.MySQLSectionDao;
import dao.impl.mysql.MySQLTopicsDao;
import pojo.Section;
import pojo.Stats;
import pojo.Topic;
import pojo.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Сервлет, для вывода раздела - темы и подразделы.
 */
public class SectionsServlet extends HttpServlet {

    static Logger log = Logger.getLogger(ForumsServlet.class.getName());
    static MySQLTopicsDao topicsDao = new MySQLTopicsDao();
    static MySQLSectionDao sectionDao = new MySQLSectionDao();
    Stats stat = new Stats();

    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int sectionId = 0;
        int numberOfTopicsOnPage = 10;
        int page = 0;
        User user = (User) req.getSession().getAttribute("user");
        if(req.getParameter("p") == null) {
            page = 1;
        } else {
            try {
                page = Integer.parseInt(req.getParameter("p"), 10);
            } catch (NumberFormatException nfe) {
                req.setAttribute("error", "УПС :( Такой страницы нет.");
                req.getRequestDispatcher("/404.jsp").forward(req,resp);
            }
        }
        try {
            sectionId = Integer.parseInt(req.getParameter("id"), 10);
        } catch (NumberFormatException nfe) {
            req.setAttribute("error", "УПС :( Раздела с таким id не существует.");
            req.getRequestDispatcher("/404.jsp").forward(req,resp);
        }
        resp.setContentType("text/html;charset=utf-8");
        Section currentSection = sectionDao.getSection(sectionId);
        int count = sectionDao.getPagesNum(currentSection, numberOfTopicsOnPage);
        List<Topic> topics = sectionDao.getAllTopicsPaging(currentSection, (page-1)*numberOfTopicsOnPage, numberOfTopicsOnPage);
        req.setAttribute("section", currentSection);
        req.setAttribute("subsections", currentSection.getSubsections());
        req.setAttribute("topics", topics);
        req.setAttribute("numberOfPages", count);
        req.setAttribute("currentPage", page);

        req.setAttribute("stats", stat);
        req.setAttribute("user", user);
        req.getServletContext().getRequestDispatcher("/section.jsp").forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req,resp);
    }
}

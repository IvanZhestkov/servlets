package web;

import org.apache.log4j.Logger;
import dao.impl.mysql.MySQLForumsDao;
import dao.impl.mysql.MySQLSectionDao;
import pojo.Forum;
import pojo.Section;
import pojo.Stats;
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
 * Сервлет для вывода списка форумов и их разделов.
 */
public class ForumsServlet extends HttpServlet {

    static Logger log = Logger.getLogger(ForumsServlet.class.getName());
    static MySQLForumsDao forumsDao = new MySQLForumsDao();
    static MySQLSectionDao sectionDao = new MySQLSectionDao();
    Stats stat = new Stats();

    public void common(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        List<Forum> forumList = null;
        User user = (User) req.getSession().getAttribute("user");
        try {
            forumList = forumsDao.getAllForums();
            Iterator<Forum> forumIterator = forumList.iterator();
            Forum temp = null;
            while(forumIterator.hasNext()) {
                temp = forumIterator.next();
                temp.setForumSections(sectionDao.getAllSections(temp));
                Iterator<Section> sectionIterator = temp.getForumSections().iterator();
                Section section = null;
                while(sectionIterator.hasNext()) {
                    section = sectionIterator.next();
                    if(sectionDao.getLastTopic(section) != null) {
                        section.setLastTopic(sectionDao.getLastTopic(section));
                    }
                }
            }
        } catch (SQLException sqle) {
            log.error("Ошибка в чтении списка форумов", sqle);
        }

        req.setAttribute("stats", stat);
        req.setAttribute("forums", forumList);
        req.setAttribute("user", user);
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        common(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        common(req, resp);
    }
}

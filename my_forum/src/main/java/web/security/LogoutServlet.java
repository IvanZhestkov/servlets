package web.security;

import org.apache.log4j.Logger;
import dao.DaoFactory;
import dao.interfaces.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет, отвечающий за выход из сессии пользователя
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    static Logger log = Logger.getLogger(LogoutServlet.class.getName());
    static UserDao userDao = DaoFactory.getDaoFactory(1).getUserDao();

    protected void doProcess(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.getSession().removeAttribute("user");
        req.getSession().invalidate();
        resp.sendRedirect("main");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    private boolean isRemembered(Cookie[] cookies) {
        for(Cookie ck: cookies) {
            if(ck.getName().equals("rememberme")) {
                if(ck.getValue() != null) return true;
            }
        }
        return false;
    }


    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

}

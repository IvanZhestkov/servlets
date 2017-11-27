package web.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import dao.impl.mysql.MySQLUserDao;
import pojo.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Сервлет, отвечающий за процесс аутентификации пользователя
 * на сайте.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    static Logger log = Logger.getLogger(LoginServlet.class.getName());
    static MySQLUserDao userDao = new MySQLUserDao();

    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userLogin = req.getParameter("user_login");
        String password = req.getParameter("user_password");
        String referer = req.getHeader("Referer");
        HttpSession session = req.getSession();


        if(userLogin != null) {
            if(password != null) {
                User user = userDao.getUser(userLogin, DigestUtils.md5Hex(password));
                if(user != null) {
                    session.setAttribute("user", user);
                    resp.sendRedirect("main");
                    log.info("Перенаправление: " + referer);
                } else {
                    req.setAttribute("error_message", "Неправильный логин или пароль.");
                    req.getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
                    return;
                }
            } else {
                req.setAttribute("error_message", "Не введен пароль");
                req.getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }
        } else {
            req.setAttribute("error_message", "Не введен логин");
            req.getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
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

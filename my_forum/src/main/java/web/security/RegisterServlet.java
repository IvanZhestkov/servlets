package web.security;

import org.apache.log4j.Logger;
import dao.impl.mysql.MySQLTokenDao;
import dao.impl.mysql.MySQLUserDao;
import pojo.user.Member;
import pojo.user.Token;
import pojo.user.User;
import pojo.user.UserTypes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

/**
 * Сервлет для регистрации пользователей
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    Logger log = Logger.getLogger(RegisterServlet.class.getName());
    static MySQLUserDao userDao = new MySQLUserDao();
    static MySQLTokenDao tokenDao = new MySQLTokenDao();

    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        log.info("Начало регистрации пользователя");
        User newUser = new Member();
        newUser.setUserRole(UserTypes.MEMBER.toString());
        newUser.setUsername(req.getParameter("username"));
        newUser.setUserFname(req.getParameter("fname"));
        newUser.setUserLname(req.getParameter("lname"));
        newUser.setPasswordMd5(req.getParameter("password"));
        newUser.setUserEmail(req.getParameter("email"));

        log.debug("Сохранение пользователя "+newUser.getUsername());
        userDao.createUser(newUser);
        newUser = userDao.getUserByName(newUser.getUsername());
        log.debug("Сохранение пользователя " + newUser.getUsername() + " прошло успешно");

        log.debug("Создание токена");
        Token token = new Token();
        token.setUuid(java.util.UUID.randomUUID().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date());
        calendar.add(Calendar.DATE, 5);
        java.util.Date now_plus_5_days = calendar.getTime();
        token.setDeleteDate(now_plus_5_days);
        token.setUser(newUser);
        tokenDao.create(token);
        log.debug("Токен успешно создан");
        req.setAttribute("email", req.getParameter("email"));
        resp.sendRedirect("login?user_login="+req.getParameter("username")+"&user_password="+req.getParameter("password"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }
}

package web;

import org.apache.commons.codec.digest.DigestUtils;
import dao.DaoFactory;
import dao.interfaces.UserDao;
import pojo.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет для изменения информации о пользователе
 */
@WebServlet("/settings")
public class EditUserServlet extends HttpServlet {

    static UserDao userDao = DaoFactory.getDaoFactory(1).getUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("user", user);
        req.getServletContext().getRequestDispatcher("/settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        User user = (User) req.getSession().getAttribute("user");
        if(!req.getParameter("fname").equals("")) {
            user.setUserFname(req.getParameter("fname"));
        }
        if(!req.getParameter("lname").equals("")) {
            user.setUserLname(req.getParameter("lname"));
        }
        if(!req.getParameter("email").equals("")) {
            user.setUserEmail(req.getParameter("email"));
        }
        if(!req.getParameter("password").equals("")) {
            user.setPasswordMd5(DigestUtils.md5Hex(req.getParameter("password")));
        }
        if(!req.getParameter("info").equals("")) {
            user.setUserInfo(req.getParameter("info"));
        }
        userDao.updateUser(user);
        resp.sendRedirect(req.getHeader("Referer"));
    }
}

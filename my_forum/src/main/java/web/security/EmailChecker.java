package web.security;

import dao.impl.mysql.MySQLUserDao;
import pojo.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Сервлет, проверяющий на существование email-адресса в базе
 */
@WebServlet("/checkemail")
public class EmailChecker extends HttpServlet {

    static MySQLUserDao userDao = new MySQLUserDao();

    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String email = req.getParameter("email");
        if(email.trim().equals("")) return;
        User user = userDao.getUserByEmail(email);
        PrintWriter pw = resp.getWriter();
        if(user == null) {
            resp.setStatus(200);
            pw.write("<span style=color:green>Email-адрес свободен</span>");
        } else {
            resp.setStatus(200);
            pw.write("<span style=color:red>Email-адрес занят</span>");
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

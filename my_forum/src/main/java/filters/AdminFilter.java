package filters;

import pojo.user.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Фильтр, отвечающий за перенаправление пользователей не админов
 */
@WebFilter(filterName = "adminFilter", urlPatterns = {"/admin", "/admin/*"})
public class AdminFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        User user = ((User) httpRequest.getSession().getAttribute("user"));
        if (user != null) {
            if (user.isAdmin()) {
                chain.doFilter(request, response);
            } else {
                httpRequest.setAttribute("error_code", 403);
                httpRequest.setAttribute("error_message", "Вы не являетесь администратором и не можете просматривать данную страницу.");
                httpRequest.getServletContext().getRequestDispatcher("/404.jsp").forward(httpRequest, httpResponse);
                return;
            }

        } else {
            httpResponse.sendRedirect("login");
        }
    }

}

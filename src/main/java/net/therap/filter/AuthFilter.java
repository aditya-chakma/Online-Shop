package net.therap.filter;

import net.therap.util.AccesChecker;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author al.imran
 * @since 11/05/2021
 */
@WebFilter(urlPatterns = "/*", dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class AuthFilter implements Filter {

    private static final String LOGIN = "login";
    private static final String PUBLIC_PATH = "/public";
    private static final String USER_PATH = "/user";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        boolean isPublicPath = req.getRequestURI().startsWith(PUBLIC_PATH);
        boolean isLoginPath = req.getRequestURI().contains(LOGIN);
        boolean isUserPath = req.getRequestURI().contains(USER_PATH);
        boolean isLoggedIn = AccesChecker.isLoggedin(req.getSession());

        if (isLoginPath || isUserPath || isLoggedIn || isPublicPath) {
            filter.doFilter(request, response);

        } else {
            resp.sendRedirect(LOGIN);
        }
    }

    @Override
    public void destroy() {
    }
}

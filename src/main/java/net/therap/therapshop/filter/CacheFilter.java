package net.therap.therapshop.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author aditya.chakma
 * @since 6/11/21
 */
@WebFilter(urlPatterns = "/*", dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class CacheFilter implements Filter {

    private static final String HEADER_NAME = "Cache-Control";
    private static final String HEADER_VALUE = "no-cache, no-store, must-revalidate";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ((HttpServletResponse) response).setHeader(HEADER_NAME, HEADER_VALUE);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

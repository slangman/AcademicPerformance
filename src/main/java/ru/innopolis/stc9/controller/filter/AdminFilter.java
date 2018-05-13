package ru.innopolis.stc9.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession httpSession = ((HttpServletRequest) request).getSession();
        if ((httpSession.getAttribute("login")!=null)&&(httpSession.getAttribute("role").equals("Admin"))) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpServletResponce = (HttpServletResponse) response;
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            httpServletResponce.sendRedirect(httpServletRequest.getContextPath()+"/login?errorMsg=noAuth");
        }
    }

    @Override
    public void destroy() {

    }
}

package ru.innopolis.stc9.servlets;


import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger("defaultLog");
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("Unicode");
        resp.getWriter().println("Шалом<");
        logger.info("Шалом printed");
    }
}

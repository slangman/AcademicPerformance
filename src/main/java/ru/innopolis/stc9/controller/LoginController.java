package ru.innopolis.stc9.controller;

import org.mindrot.jbcrypt.BCrypt;
import ru.innopolis.stc9.db.dao.UserDAOImpl;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("message", "Hello");
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("userName");
        String password = req.getParameter("userPassword");
        if (userService.auth(login, password)) {
            String role = userService.getRole(login);
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("role", role);
            switch (role) {
                case("Admin"): resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                break;
                case("Teacher"): resp.sendRedirect(req.getContextPath() + "/teacher/dashboard");
                break;
                case("Student"): resp.sendRedirect(req.getContextPath() + "/student/dashboard");
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/login?errorMsg=authError");
        }
    }

}

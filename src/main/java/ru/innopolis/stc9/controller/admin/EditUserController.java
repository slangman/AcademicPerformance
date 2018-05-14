package ru.innopolis.stc9.controller.admin;

import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserController extends HttpServlet {
    UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("user-login");

        if (login != null && !login.isEmpty()) {
            User user = userService.getUserByLogin(login);
            req.setAttribute("user", user);
        }

        req.getRequestDispatcher("/admin-tools/edit-user.jsp").forward(req, resp);

    }
}

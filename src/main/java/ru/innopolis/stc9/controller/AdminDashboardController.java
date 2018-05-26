package ru.innopolis.stc9.controller;

import ru.innopolis.stc9.service.AdminService;
import ru.innopolis.stc9.service.StudentService;
import ru.innopolis.stc9.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminDashboardController extends HttpServlet {

    private AdminService adminService = new AdminService();
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String)req.getSession().getAttribute("login");
        String helloMessage = userService.getHelloMessage(login);
        req.setAttribute("helloMessage", helloMessage);
        req.setAttribute("users", adminService.getUsersList());
        req.setAttribute("courses", adminService.getCourses());
        req.getRequestDispatcher("/admin-dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

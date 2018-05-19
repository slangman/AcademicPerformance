package ru.innopolis.stc9.controller;

import ru.innopolis.stc9.service.StudentService;
import ru.innopolis.stc9.service.TeacherService;
import ru.innopolis.stc9.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TeacherDashboardController extends HttpServlet {
    private TeacherService teacherService = new TeacherService();
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = (String)req.getSession().getAttribute("login");

        Map<Integer, String> courses = teacherService.getCourses(login);
        String helloMessage = userService.getHelloMessage(login);

        req.setAttribute("courses", courses);
        req.setAttribute("helloMessage", helloMessage);
        req.getRequestDispatcher("/teacher-dashboard.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}

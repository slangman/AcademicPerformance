package ru.innopolis.stc9.controller;

import ru.innopolis.stc9.pojo.Course;
import ru.innopolis.stc9.service.StudentService;
import ru.innopolis.stc9.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentDashboardController extends HttpServlet {
    private StudentService studentService = new StudentService();
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = (String)req.getSession().getAttribute("login");

        List<String> courses = studentService.getCoursesNames(login);
        String helloMessage = userService.getHelloMessage(login);

        req.setAttribute("courses", courses);
        req.setAttribute("helloMessage", helloMessage);
        req.getRequestDispatcher("/student-dashboard.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}

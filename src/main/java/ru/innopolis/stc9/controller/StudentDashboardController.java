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

public class StudentDashboardController extends HttpServlet {
    private StudentService studentService = new StudentService();
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = (String)req.getSession().getAttribute("login");
        int id = studentService.getStudentId(login);

        ArrayList<String> courses = new ArrayList<>();
        for (Course course : studentService.getCourses(id)) {
            courses.add(course.getName());
        }

        String helloMessage = userService.getHelloMessage(login);

        req.setAttribute("courses", courses);
        req.setAttribute("helloMessage", helloMessage);
        req.getRequestDispatcher("/student-dashboard.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}

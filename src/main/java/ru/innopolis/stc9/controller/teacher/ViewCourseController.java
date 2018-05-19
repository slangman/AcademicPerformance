package ru.innopolis.stc9.controller.teacher;

import ru.innopolis.stc9.service.CourseService;
import ru.innopolis.stc9.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ViewCourseController extends HttpServlet {
    private static final CourseService courseService = new CourseService();
    private static final StudentService studentService = new StudentService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int courseId=Integer.parseInt(req.getParameter("courseid"));
        String courseName = courseService.getCourseName(courseId);
        Map<String, String> tasks = courseService.getTasks(courseId);
        Map<String, Double> students = courseService.getStudents(courseId);
        req.setAttribute("courseName", courseName);
        req.setAttribute("tasks", tasks);
        req.setAttribute("students", students);
        req.getRequestDispatcher("/teacher-tools/view-course.jsp").forward(req, resp);
    }
}

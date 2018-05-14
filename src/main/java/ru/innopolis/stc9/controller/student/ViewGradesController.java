package ru.innopolis.stc9.controller.student;

import ru.innopolis.stc9.service.CourseService;
import ru.innopolis.stc9.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ViewGradesController extends HttpServlet {
    private static final CourseService courseService = new CourseService();
    private static final StudentService studentService = new StudentService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String)req.getSession().getAttribute("login");
        int studentId = studentService.getStudentId(login);
        String courseName = req.getParameter("course-name");
        int courseId = courseService.getCourseId(courseName);

        if (studentId > 0 && courseId > 0) {
            Map<String, Integer> grades = studentService.getGradesByCourse(studentId, courseId);
            req.setAttribute("grades", grades);
        }
        req.setAttribute("courseName", courseName);
        req.getRequestDispatcher("/student-tools/view-grades.jsp").forward(req, resp);

    }
}

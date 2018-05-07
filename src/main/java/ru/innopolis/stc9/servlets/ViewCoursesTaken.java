package ru.innopolis.stc9.servlets;

import ru.innopolis.stc9.pojo.Course;
import ru.innopolis.stc9.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ViewCoursesTaken extends HttpServlet {
    private StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("Unicode");
        String studentId = req.getParameter("student-id");
        if (studentId!=null) {
            List<Course> courses = studentService.getCourses(Integer.parseInt(studentId));
            for (Course course: courses) {
                resp.getWriter().println(course.getName());
            }
        }

        //req.getRequestDispatcher("/view-courses-taken.jsp").forward(req, resp);
        //resp.getWriter().println("View Couses Taken");
        //resp.getWriter().println("Шалом");
    }
}

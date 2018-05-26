package ru.innopolis.stc9.controller.admin;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.service.AdminService;
import ru.innopolis.stc9.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditCourseController extends HttpServlet {

    private CourseService courseService = new CourseService();
    private AdminService adminService = new AdminService();
    private static Logger logger = Logger.getLogger("defaultLog");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        int courseId = Integer.parseInt(req.getParameter("course-id"));
        logger.info(courseId);
        if (operation!=null && !operation.isEmpty()) {
            if (operation.equals("new-course")) {
                req.getSession().setAttribute("operation", "new-course");
            }
            if (operation.equals("edit-existing")) {
                req.getSession().setAttribute("operation", "edit-existing");
                req.getSession().setAttribute("courseId", courseId);
            }
        }
        if (courseId > 0) {
            req.setAttribute("course", courseService.getCourse(courseId));
        }

        req.getRequestDispatcher("/admin-tools/edit-course.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = adminService.addOrEditCourse(req);
        resp.sendRedirect(req.getContextPath() + msg);
    }
}

package ru.innopolis.stc9.controller.teacher;

import ru.innopolis.stc9.pojo.Task;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.AdminService;
import ru.innopolis.stc9.service.TaskService;
import ru.innopolis.stc9.service.TeacherService;
import ru.innopolis.stc9.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditTaskController extends HttpServlet {
    UserService userService = new UserService();
    AdminService adminService = new AdminService();
    private TaskService taskService = new TaskService();
    private TeacherService teacherService = new TeacherService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        String id = req.getParameter("task-id");
        if (operation!=null && !operation.isEmpty()) {
            if (operation.equals("new-task")) {
                req.getSession().setAttribute("operation", "new-task");
                req.getSession().setAttribute("courseId", req.getParameter("courseid"));
            }
            if (operation.equals("edit-existing")) {
                req.getSession().setAttribute("operation", "edit-existing");
                req.getSession().setAttribute("taskId", id);
            }
        }
        if (id!=null && !id.isEmpty()) {
            Task task = taskService.getTask(Integer.parseInt(id));
            req.setAttribute("task", task);
        }
        req.getRequestDispatcher("/teacher-tools/edit-task.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String taskName = req.getParameter("taskName");
        String taskDescription = req.getParameter("taskDescription");
        String operation="";
        String taskId = "";
        String courseId="";

        if (req.getSession().getAttribute("operation")!=null) {
            operation = (String)req.getSession().getAttribute("operation");
            req.getSession().setAttribute("operation", null);
        }
        System.out.println(operation);

        if (req.getSession().getAttribute("taskId")!=null) {
            taskId = (String)req.getSession().getAttribute("taskId");
            req.getSession().setAttribute("taskId", null);
        }

        String msg="";
        if (operation.equals("edit-existing")) {
            boolean updateSuccess = teacherService.updateTask(taskId, taskName, taskDescription);
            if (updateSuccess) {
                msg = "updated";
            }
        }
        if (operation.equals("new-task")) {
            if (req.getSession().getAttribute("courseId")!=null) {
                courseId=(String)req.getSession().getAttribute("courseId");

                boolean addSuccess = teacherService.addTask(taskName, taskDescription, Integer.parseInt(courseId));
                if (addSuccess) {
                    msg = "task-added";
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "?operation=" + operation + "&courseid=" + courseId + "&task-id=" + taskId + "&msg=" + msg + "&err=");
    }
}

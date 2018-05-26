package ru.innopolis.stc9.service;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.db.dao.*;
import ru.innopolis.stc9.pojo.Course;
import ru.innopolis.stc9.pojo.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherService {
    private static final Logger logger = Logger.getLogger("defaultLog");

    private TeacherDAO teacherDAO = new TeacherDAO();
    private UserDAOImpl userDAO = new UserDAOImpl();
    private CourseDAOImpl courseDAO = new CourseDAOImpl();
    private TaskDAOImpl taskDAO = new TaskDAOImpl();

    public List<Course> getCoursesById(int teacherId) {
        return teacherDAO.getCourses(teacherId);
    }

    public Map<Integer, String> getCourses(String login) {
        Map<Integer, String> result = new HashMap<>();
        List<Course> courses = teacherDAO.getCourses(getTeacherId(login));
        if (courses!=null && !courses.isEmpty()) {
            for (Course course : courses) {
                result.put(courseDAO.getCourseId(course.getName()), course.getName());
            }
        }

        return result;
    }

    public boolean updateTask(String id, String taskName, String taskDescription) {
        int taskId = Integer.parseInt(id);
        int courseId = taskDAO.getTaskById(taskId).getCourseId();
        Task newTask = new Task(taskName, taskDescription, courseId);
        return taskDAO.updateTask(taskId, newTask);
    }

    public boolean addTask(String taskName, String taskDescription, int courseId) {
        Task task = new Task(taskName, taskDescription, courseId);
        return taskDAO.addTask(task);
    }

    public int getTeacherId(String login) {
        int result = 0;
        if (login!=null && !login.isEmpty()) {
            result = userDAO.getUserId(login);
        }
        return result;
    }

}

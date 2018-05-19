package ru.innopolis.stc9.service;

import ru.innopolis.stc9.db.dao.CourseDAO;
import ru.innopolis.stc9.db.dao.CourseDAOImpl;
import ru.innopolis.stc9.db.dao.TeacherDAO;
import ru.innopolis.stc9.db.dao.UserDAOImpl;
import ru.innopolis.stc9.pojo.Course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherService {
    TeacherDAO teacherDAO = new TeacherDAO();
    UserDAOImpl userDAO = new UserDAOImpl();
    CourseDAOImpl courseDAO = new CourseDAOImpl();

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

    public int getTeacherId(String login) {
        int result = 0;
        if (login!=null && !login.isEmpty()) {
            result = userDAO.getUserId(login);
        }
        return result;
    }

}

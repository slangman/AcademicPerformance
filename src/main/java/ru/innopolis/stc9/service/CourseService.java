package ru.innopolis.stc9.service;


import ru.innopolis.stc9.db.dao.CourseDAOImpl;
import ru.innopolis.stc9.db.dao.StudentDAO;
import ru.innopolis.stc9.pojo.Course;
import ru.innopolis.stc9.pojo.Student;
import ru.innopolis.stc9.pojo.Task;
import ru.innopolis.stc9.pojo.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseService {
    private CourseDAOImpl courseDao = new CourseDAOImpl();
    private StudentDAO studentDAO = new StudentDAO();

    public Course getCourse(int id) {
        return courseDao.getCourseById(id);
    }

    public String getCourseName(int courseId) {
        String result=null;
        if (courseId>0) {
            result=courseDao.getCourseById(courseId).getName();
        }
        return result;
    }

    public List<Task> getTasks(int courseId) {
        List<Task> tasks = courseDao.getTasks(courseId);
        return tasks;
    }

    public Map<String, String> getTasksOld(int courseId) {
        Map<String,String> result = new HashMap<>();
        List<Task> tasks = courseDao.getTasks(courseId);
        for (Task task : tasks) {
            result.put(task.getName(), task.getDescription());
        }
        return result;
    }

    public Map<String, Double> getStudents(int courseId) {
        Map<String, Double> result = new HashMap<>();
        List<User> students = courseDao.getStudentsOnCourse(courseId);
        for (User student : students) {
            String studentName = student.getFirstName() + " " + student.getLastName();
            double averageGrade = studentDAO.getAverageGradeByCourse(studentDAO.getUserId(student.getLogin()), courseId);
            result.put(studentName, averageGrade);
        }
        return result;
    }

    //TODO: отрефакторить
    public int getCourseId(String courseName) {
        return courseDao.getCourseId(courseName);
    }


}

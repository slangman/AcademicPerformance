package ru.innopolis.stc9.service;

import ru.innopolis.stc9.db.dao.StudentDAO;
import ru.innopolis.stc9.pojo.Course;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentService {
    StudentDAO studentDao = new StudentDAO();

    public List<Course> getCourses(int studentId) {
        return studentDao.getCourses(studentId);
    }

    public Map<String, Integer> getGradesByCourse(int studentId, int courseId) {
        Map<String, Integer> result = new HashMap<String, Integer>();
        if (studentId>0 && courseId>0) {
            result = studentDao.getGradesByCourse(studentId, courseId);
        }
        return result;
    }

    public int getStudentId(String login) {
        int result = 0;
        if (login!=null && !login.isEmpty()) {
            result = studentDao.getUserId(login);
        }
        return result;
    }
}

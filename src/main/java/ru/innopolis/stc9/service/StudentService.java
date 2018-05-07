package ru.innopolis.stc9.service;

import ru.innopolis.stc9.db.dao.StudentDAO;
import ru.innopolis.stc9.pojo.Course;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
    StudentDAO studentDao = new StudentDAO();

    public List<Course> getCourses(int studentId) {
        try {
            return studentDao.getCourses(studentId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

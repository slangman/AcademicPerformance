package ru.innopolis.stc9.db.dao;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerJDBCImpl;
import ru.innopolis.stc9.pojo.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private static ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();
    final static Logger logger = Logger.getLogger("defaultLog");

    public boolean addCourse(String courseName, int teacherId) throws SQLException {
        Course course = new Course(courseName, teacherId);
        CourseDAOImpl courseDao = new CourseDAOImpl();
        return courseDao.addCourse(course);
    }

    public List<Course> getCourses(int teacherId) throws SQLException {
        ArrayList<Course> result = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM course WHERE teacherid = ?"
        );
        statement.setInt(1, teacherId);
        ResultSet resultSet = statement.executeQuery();
        connection.close();
        while (resultSet.next()) {
            CourseDAOImpl courseDAO = new CourseDAOImpl();
            Course course = courseDAO.getCourseById(resultSet.getInt("courseid"));
            result.add(course);
        }
        return result;
    }
}
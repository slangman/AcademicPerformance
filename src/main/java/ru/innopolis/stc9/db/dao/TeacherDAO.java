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
    static final Logger logger = Logger.getLogger("defaultLog");

    public boolean addCourse(String courseName, int teacherId) {
        Course course = new Course(courseName, teacherId);
        CourseDAOImpl courseDao = new CourseDAOImpl();
        return courseDao.addCourse(course);
    }

    public List<Course> getCourses(int teacherId)  {
        ArrayList<Course> result = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM course WHERE teacherid = ?"
        )) {
            statement.setInt(1, teacherId);
            getCoursesFromResultSet(result, statement);
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    static void getCoursesFromResultSet(ArrayList<Course> result, PreparedStatement statement) {
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                CourseDAOImpl courseDAO = new CourseDAOImpl();
                Course course = courseDAO.getCourseById(resultSet.getInt("id"));
                result.add(course);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}

package ru.innopolis.stc9.db.dao;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerJDBCImpl;
import ru.innopolis.stc9.pojo.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDAOImpl implements CourseDAO {

    private static final Logger logger = Logger.getLogger("defaultLog");
    private static ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();

    @Override
    public Course getCourseByName(String name) {
        Connection connection = connectionManager.getConnection();
        Course course = null;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM course WHERE name = ?")) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    course = new Course(
                            resultSet.getString("name"),
                            resultSet.getInt("teacherId"));
                    if (resultSet.getString("description") != null) {
                        course.setDescription(resultSet.getString("description"));
                    }
                } else {
                    logger.warn("Course " + name + " not found.");
                }
            }
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return course;
    }

    @Override
    public Course getCourseById(int id) {
        Connection connection = connectionManager.getConnection();
        Course course = null;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM course WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    course = new Course(
                            resultSet.getString("name"),
                            resultSet.getInt("teacherId"));
                    if (resultSet.getString("description") != null) {
                        course.setDescription(resultSet.getString("description"));
                    }
                } else {
                    logger.warn("Course with id " + id + " not found.");
                }
            }
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return course;
    }


    @Override
    public boolean addCourse(Course course) {
        boolean result = false;
        Connection connection = connectionManager.getConnection();
        if (getCourseByName(course.getName()) != null) {
            logger.warn("Course with name " + course.getName() + " is already exists.");
        } else {
            if (checkIfTeacherExists(course.getTeacherId())) {
                try (PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO course (name, teacherid, description)" +
                                "VALUES(?, ?, ?) RETURNING id"
                )) {
                    statement.setString(1, course.getName());
                    statement.setInt(2, course.getTeacherId());
                    statement.setString(3, course.getDescription());
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            result = true;
                        }
                    }
                    connection.close();
                    logger.info("Course " + course.getName() + " successfully added.");
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                }
            } else {
                logger.warn("User id " + course.getTeacherId() + " not found or user role is not a Teacher.");
            }
        }
        return result;
    }

    @Override
    public boolean updateCourse(int id, Course newCourse) {
        boolean result = false;
        Connection connection = connectionManager.getConnection();
        if (getCourseById(id) != null) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE course SET name = ?, teacherId = ?, description = ?" +
                            "WHERE id = ? RETURNING id")) {
                statement.setString(1, newCourse.getName());
                statement.setInt(2, newCourse.getTeacherId());
                statement.setString(3, newCourse.getDescription());
                statement.setInt(4, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        logger.info("Course with id " + id + " successfully updated.");
                        result = true;
                    }
                }
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.warn("Course with id " + id + " not found.");
        }
        return result;
    }

    @Override
    public void deleteCourseById(int id) {
        if (getCourseById(id) != null) {
            Connection connection = connectionManager.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM course WHERE id = ?"
            )) {
                statement.setInt(1, id);
                statement.execute();
                logger.info("Course with id " + id + " successfully deleted.");
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.warn("Course with id " + id + " not found.");
        }
    }

    private boolean checkIfTeacherExists(int id) {
        boolean result = false;
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE id = ? AND roleid = 2"
        )) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = true;
                }
            }
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    //TODO: отрефакторить!
    public int getCourseId(String courseName) {
        int result = 0;
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM course WHERE name = ?"
        )) {
            statement.setString(1, courseName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result=resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }
}

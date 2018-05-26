package ru.innopolis.stc9.db.dao;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerJDBCImpl;
import ru.innopolis.stc9.db.dao.mapper.CourseMapper;
import ru.innopolis.stc9.pojo.Course;
import ru.innopolis.stc9.pojo.Student;
import ru.innopolis.stc9.pojo.Task;
import ru.innopolis.stc9.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CourseDAOImpl implements CourseDAO {

    private static final Logger logger = Logger.getLogger("defaultLog");
    private static ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();
    UserDAOImpl userDAO = new UserDAOImpl();

    public List<Course> getCoursesList() {
        List<Course> result = null;
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM course"
        )) { try (ResultSet resultSet = statement.executeQuery()) {
            result = CourseMapper.getCourseListFromResultSet(resultSet);
        }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Course getCourseByName(String name) {
        Course course = null;
        if (name!=null && !name.isEmpty()) {
            Connection connection = connectionManager.getConnection();
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
                        logger.info("Course " + name + " successfully returned.");
                    } else {
                        logger.warn("Course " + name + " not found.");
                    }
                }
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
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
                    logger.info("Course with id " + id +" successfully returned.");
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
        if (id > 0) {
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
        }
        return result;
    }

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

    public List<Task> getTasks(int id) {
        List<Task> result = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM task WHERE courseid=?"
        )) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    Task task = new Task(
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            id);
                    int taskId = resultSet.getInt("id");
                    task.setId(taskId);
                    result.add(task);
                }
            }
            connection.close();
            } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public List<User> getStudentsOnCourse(int courseId) {
        List<User> result = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT studentid FROM studentsatcourse WHERE courseid = ?"
        )) {
            statement.setInt(1, courseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int studentId = resultSet.getInt("studentid");
                    result.add(userDAO.getUserById(studentId));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }
}

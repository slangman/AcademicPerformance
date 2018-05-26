package ru.innopolis.stc9.db.dao;

/**
 * Implementation of TaskDAO interface.
 * Operates with Task data stored in database.
 *
 * @author Daniil Ivantsov
 * @version 1.0
 */

import org.apache.log4j.Logger;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerJDBCImpl;
import ru.innopolis.stc9.pojo.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskDAOImpl implements TaskDAO {

    private static ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();
    static final Logger logger = Logger.getLogger("defaultLog");

    /**
     * Returns task object stored in database.
     * @param id
     * @return <tt>Task</tt> object.
     */
    @Override
    public Task getTaskById(int id) {
        Connection connection = connectionManager.getConnection();
        Task task = null;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM task WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    task = new Task(
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getInt("courseId"));
                    logger.info("Task with id " + id + " successfully returned.");
                } else {
                    logger.error("Task with id " + id + " not found.");
                }
            }
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return task;
    }

    /**
     * Returns Task object stored in database.
     * @param name
     * @param courseId
     * @return a <tt>Task</tt> object.
     */
    public Task getTask(String name, int courseId) {
        Connection connection = connectionManager.getConnection();
        Task task = null;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM task WHERE name = ? AND courseId = ?")) {
            statement.setString(1, name);
            statement.setInt(2, courseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    task = new Task(
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getInt("courseId"));
                    logger.info("Task " + name + " with courseId " + courseId + " successfully returned.");
                } else {
                    logger.info("Task " + name + " with courseId " + courseId + "not found.");
                }
            }
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return task;
    }

    /**
     * Inserts Task data into database.
     * @param task
     * @return true if success.
     */
    @Override
    public boolean addTask(Task task) {
        Connection connection = connectionManager.getConnection();
        boolean result = false;
        if (getTask(task.getName(), task.getCourseId()) == null) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO task (name, description, courseid)" +
                            "VALUES(?, ?, ?) RETURNING id")) {
                statement.setString(1, task.getName());
                statement.setString(2, task.getDescription());
                statement.setInt(3, task.getCourseId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        result = true;
                        logger.info("Task " + task.getName() + " successfully added.");
                    }
                }
                connection.close();
                return result;
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Updates Task data stored in database.
     * @param id
     * @param newTask
     * @return true if success.
     */
    @Override
    public boolean updateTask(int id, Task newTask) {
        boolean result = false;
        if (getTaskById(id) != null && newTask != null) {
            Connection connection = connectionManager.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE task SET name = ?, description = ?, courseId = ?" +
                            "WHERE id = ? RETURNING id")) {
                statement.setString(1, newTask.getName());
                statement.setString(2, newTask.getDescription());
                statement.setInt(3, newTask.getCourseId());
                statement.setInt(4, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        result = true;
                        logger.info("Task with id " + id + " successfully updated.");
                    }
                }
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return result;
    }

    @Override
    public void deleteTaskById(int id) {
        if (getTaskById(id) != null) {
            Connection connection = connectionManager.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM task WHERE id = ?"
            )) {
                statement.setInt(1, id);
                statement.execute();
                connection.close();
                logger.info("Task with id " + id + " successfully deleted.");
            } catch (SQLException e) {
                logger.info("Can not delete task with id " + id);
                logger.error(e.getMessage());
            }
        }
    }

}

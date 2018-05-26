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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDAO extends UserDAOImpl {

    private static ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();
    static final Logger logger = Logger.getLogger("defaultLog");

    /*public StudentDAO(int id) {
        this.id = id;
    }*/

    public List<Course> getCourses(int studentId)  {
        ArrayList<Course> result = new ArrayList<>();
        Connection connection = connectionManager.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT courseid AS id FROM studentsatcourse WHERE studentid = ?"
        )) {
            statement.setInt(1, studentId);
            TeacherDAO.getCoursesFromResultSet(result, statement);
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public Map<String, Integer> getGradesByCourse(int studentId, int courseId) {
        Connection connection = connectionManager.getConnection();
        Map<String, Integer> result = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT task.name, grade.value FROM grade " +
                        "INNER JOIN task ON grade.taskid = task.id " +
                        "WHERE studentid=? AND courseid=?"
        )) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    Integer value = resultSet.getInt("value");
                    result.put(name, value);
                }
            }
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    public int getGradeByTask(int studentId, int taskId) {
        int result = -1;
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
            "SELECT value FROM grade WHERE studentid = ? AND taskid = ?"
        )) {
            statement.setInt(1, studentId);
            statement.setInt(2, taskId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt("value");
                }
            }
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    public float getAverageGradeByCourse(int studentId, int courseId) {
        float result = -1;
        Map <String, Integer> grades = getGradesByCourse(studentId, courseId);
        if (grades!=null && grades.size()>0) {
            int sum=0;
            for (Map.Entry<String, Integer> entry : grades.entrySet()) {
                int gradeValue = entry.getValue();
                sum += gradeValue;
            }
            result = ((float)sum)/grades.size();
        }
        return result;
    }

    public float getAverageGrade(int studentId) {
        float result = -1;
        ArrayList<Integer> grades = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM grade WHERE studentid = ?"
        )) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    grades.add(resultSet.getInt("value"));
                }
                if (!grades.isEmpty()) {
                    int sum = 0;
                    for (Integer grade : grades) {
                        sum += grade;
                    }
                    result = ((float)sum) / grades.size();
                }
            }
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    public boolean checkStudentOnCourse(int studentId, int courseId) {
        Connection connection = connectionManager.getConnection();
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM studentsatcourse WHERE courseid = ? AND studentid = ?"
        )) {
            statement.setInt(1, courseId);
            statement.setInt(2, studentId);
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
}

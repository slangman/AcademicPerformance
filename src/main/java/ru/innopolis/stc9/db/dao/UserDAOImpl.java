package ru.innopolis.stc9.db.dao;

import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerJDBCImpl;
import ru.innopolis.stc9.pojo.Admin;
import ru.innopolis.stc9.pojo.Student;
import ru.innopolis.stc9.pojo.Teacher;
import ru.innopolis.stc9.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    private static ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();
    final static Logger logger = Logger.getLogger("defaultLog");

    @Override
    public User getUserById(int id) throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users where id = ?"
        );
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        connection.close();
        if (resultSet.next()) {
           return getUserFromResultSet(resultSet);
        }
        return null;
    }

    public User getUserByLogin(String login) throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users where login = ?"
        );
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();
        connection.close();
        if (resultSet.next()) {
            return getUserFromResultSet(resultSet);
        }
        return null;
    }

    public User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = null;
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        int roleId = resultSet.getInt("roleid");
        switch (roleId) {
            case 1:
                user = new Admin();
                break;
            case 2:
                user = new Teacher();
                break;
            case 3:
                user = new Student();
                break;
        }
        user.setLogin(login);
        user.setPassword(password);
        if (resultSet.getString("fname") != null) {
            user.setFirstName(resultSet.getString("fname"));
        }
        if (resultSet.getString("lname") != null) {
            user.setLastName(resultSet.getString("lname"));
        }
        if (user != null) {
            if (user instanceof Admin) {
                Admin admin = (Admin) user;
                return admin;
            }
            if (user instanceof Teacher) {
                Teacher teacher = (Teacher) user;
                return teacher;
            }
            if (user instanceof Student) {
                Student student = (Student) user;
                return student;
            }
        }
        return null;
    }

    @Override
    public boolean addUser(User user) throws SQLException {
        Connection connection = connectionManager.getConnection();
        String login = user.getLogin();
        String password = user.getPassword();
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        int roleId = 0;
        if (user instanceof Admin) {
            roleId = 1;
        }
        if (user instanceof Teacher) {
            roleId = 2;
        }
        if (user instanceof Student) {
            roleId = 3;
        }
        String fname = "";
        String lname = "";
        if (user.getFirstName()!=null) {
            fname = user.getFirstName();
        }
        if (user.getLastName()!=null) {
            lname = user.getLastName();
        }
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (roleid, login, password, fname, lname)" +
                        "VALUES (?, ?, ?, ?, ?)"
        );
        statement.setInt(1, roleId);
        statement.setString(2, login);
        statement.setString(3, passwordHash);
        statement.setString(4, fname);
        statement.setString(5, lname);
        boolean result = statement.execute();
        connection.close();
        return result;
    }

    @Override
    public boolean updateUser(int id, User newUser) throws SQLException {
        Connection connection = connectionManager.getConnection();
        String login = newUser.getLogin();
        String password = newUser.getPassword();
        int roleId = 0;
        if (newUser instanceof Admin) {
            roleId = 1;
        }
        if (newUser instanceof Teacher) {
            roleId = 2;
        }
        if (newUser instanceof Student) {
            roleId = 3;
        }
        String fname = "";
        String lname = "";
        if (newUser.getFirstName()!=null) {
            fname = newUser.getFirstName();
        }
        if (newUser.getLastName()!=null) {
            lname = newUser.getLastName();
        }
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET roleid = ? login = ?, password = ?, fname = ?, lname = ?" +
                        "WHERE id = ?"
        );
        statement.setInt(1, roleId);
        statement.setString(2, login);
        statement.setString(3, password);
        statement.setString(4, fname);
        statement.setString(5, lname);
        statement.setInt(6, id);
        boolean result = statement.execute();
        connection.close();
        return result;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        if (getUserById(id) != null) {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users WHERE id = ?"
            );
            statement.setInt(1, id);
            boolean result = statement.execute();
            connection.close();
            return result;
        } else {
            return false;
        }
    }

    public int getUserId(String login) throws SQLException {
        int result = -1;
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE login = ?"
        );
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            result = resultSet.getInt("id");
        }
        return result;
    }
}

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
    private static final Logger logger = Logger.getLogger("defaultLog");

    @Override
    public User getUserById(int id) {
        Connection connection = connectionManager.getConnection();
        User result = null;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE id = ?"
        )) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                connection.close();
                if (resultSet.next()) {
                    result = getUserFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public User getUserByLogin(String login) {
        Connection connection = connectionManager.getConnection();
        User result = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login = ?"))
        {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                connection.close();
                if (resultSet.next()) {
                    result = getUserFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = null;
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        int roleId = resultSet.getInt("roleid");
        switch (roleId) {
            case 1:
                user = new Admin(login, password);
                break;
            case 2:
                user = new Teacher(login,password);
                break;
            case 3:
                user = new Student(login, password);
                break;
            default:
                break;
        }
        if (user != null) {
            if (resultSet.getString("fname") != null) {
                user.setFirstName(resultSet.getString("fname"));
            }
            if (resultSet.getString("lname") != null) {
                user.setLastName(resultSet.getString("lname"));
            }
        }
        return user;
    }

    @Override
    public boolean addUser(User user) {
        Connection connection = connectionManager.getConnection();
        boolean result = false;
        String login = user.getLogin();
        if (getUserByLogin(login) == null) {
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
            if (user.getFirstName() != null) {
                fname = user.getFirstName();
            }
            if (user.getLastName() != null) {
                lname = user.getLastName();
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (roleid, login, password, fname, lname)" +
                            "VALUES (?, ?, ?, ?, ?) RETURNING id"
            )) {
                statement.setInt(1, roleId);
                statement.setString(2, login);
                statement.setString(3, passwordHash);
                statement.setString(4, fname);
                statement.setString(5, lname);
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

    @Override
    public boolean updateUser(int id, User newUser) {
        Connection connection = connectionManager.getConnection();
        boolean result = false;
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
        if (newUser.getFirstName() != null) {
            fname = newUser.getFirstName();
        }
        if (newUser.getLastName() != null) {
            lname = newUser.getLastName();
        }
        try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE users SET roleid = ?, login = ?, password = ?, fname = ?, lname = ?" +
                            "WHERE id = ? RETURNING id"
            )) {
            statement.setInt(1, roleId);
            statement.setString(2, login);
            statement.setString(3, password);
            statement.setString(4, fname);
            statement.setString(5, lname);
            statement.setInt(6, id);
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

    @Override
    public void deleteUser(int id) {
        if (getUserById(id) != null) {
            Connection connection = connectionManager.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users WHERE id = ?"
            )) {
                statement.setInt(1, id);
                statement.execute();
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public int getUserId(String login) {
        int result = -1;
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE login = ?"
        )) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public boolean updatePassword(String login, String newPassword) {
        boolean result = false;
        if (login!=null && newPassword!=null){
            String passwordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            Connection connection = connectionManager.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE users SET password=?" +
                            "WHERE login = ? RETURNING id"
            )) {
                statement.setString(1, passwordHash);
                statement.setString(2, login);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        result = true;
                    }
                }
            } catch (SQLException e) {
                logger.info(e.getMessage());
            }
        }
        return result;
    }
}

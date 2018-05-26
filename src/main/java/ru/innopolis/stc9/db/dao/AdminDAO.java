package ru.innopolis.stc9.db.dao;
/**
 * Contains methods to operate with user data from Admin account
 *
 * @author Daniil Ivantsov
 * @version 1.0
 */

import org.apache.log4j.Logger;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.connection.ConnectionManagerJDBCImpl;
import ru.innopolis.stc9.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private static ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();
    private static final Logger logger = Logger.getLogger("defaultLog");
    UserDAOImpl userDao = new UserDAOImpl();

    public boolean addUser() {
        return false;
    }

    /**
     * Returns full list of users stored in database
     * @return a <tt>List</tt> object
     */
    public List<User> getUsersList() {
        ArrayList<User> result = new ArrayList<>();
        Connection connection = connectionManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users"
        )) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(userDao.getUserFromResultSet(resultSet));
                }
                logger.info("Users list successfully returned.");
            }
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }
}

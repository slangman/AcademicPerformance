package ru.innopolis.stc9.service;

import org.mindrot.jbcrypt.BCrypt;
import ru.innopolis.stc9.db.dao.UserDAOImpl;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.db.dao.UserDAO;


import java.sql.SQLException;

public class UserService {
    private static UserDAO userDao = new UserDAOImpl();

    public boolean auth(String login, String password) {
        UserDAOImpl userDao = new UserDAOImpl();
        try {
            User user = userDao.getUserByLogin(login);
            if (user!=null) {
                String passwordHash = user.getPassword();
                return checkPassword(password, passwordHash);
            }
        } catch (SQLException e) {
            e.getMessage();
            return false;
        }
        return false;
    }

    private boolean checkPassword (String password, String passwordHash) {
        if (BCrypt.checkpw(password, passwordHash)) {
            return true;
        } else {
            return false;
        }
    }
}

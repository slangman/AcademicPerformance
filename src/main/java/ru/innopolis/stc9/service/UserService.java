package ru.innopolis.stc9.service;

import org.mindrot.jbcrypt.BCrypt;
import ru.innopolis.stc9.db.dao.UserDAOImpl;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.db.dao.UserDAO;


import java.sql.SQLException;

public class UserService {
    private static UserDAO userDao = new UserDAOImpl();

    public boolean auth(String login, String password) {
        User user = userDao.getUserByLogin(login);
        if (user!=null) {
            String passwordHash = user.getPassword();
            return checkPassword(password, passwordHash);
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

    public String getRole(String login) {
        String role = null;
        User user = userDao.getUserByLogin(login);
        if (user!=null) {
            role = user.getClass().getSimpleName();
        }
        return role;
    }

    public String getHelloMessage(String login) {
        String helloMessage;
        String fname = userDao.getUserByLogin(login).getFirstName();
        String lname = userDao.getUserByLogin(login).getLastName();
        if (!fname.equals("") && !lname.equals("")) {
            helloMessage = "Hello, " + fname + " " + lname;
        } else {
            helloMessage = "Hello, " + login;
        }
        return helloMessage;
    }

    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }
}

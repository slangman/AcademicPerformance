package ru.innopolis.stc9.service;

import ru.innopolis.stc9.db.dao.UserDAOImpl;
import ru.innopolis.stc9.pojo.User;

import java.sql.SQLException;

public class AdminService {
    UserDAOImpl userDAO = new UserDAOImpl();

    public boolean addUser(User newUser) throws SQLException {
        return userDAO.addUser(newUser);
    }
}

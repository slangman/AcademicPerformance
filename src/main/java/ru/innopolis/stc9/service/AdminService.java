package ru.innopolis.stc9.service;

import ru.innopolis.stc9.db.dao.AdminDAO;
import ru.innopolis.stc9.db.dao.UserDAOImpl;
import ru.innopolis.stc9.pojo.User;

import java.sql.SQLException;
import java.util.List;

public class AdminService {
    private UserDAOImpl userDAO = new UserDAOImpl();
    private AdminDAO adminDao = new AdminDAO();

    public boolean addUser(User newUser) {
        return userDAO.addUser(newUser);
    }

    public List<User> getUsersList() {
        return adminDao.getUsersList();
    }
}

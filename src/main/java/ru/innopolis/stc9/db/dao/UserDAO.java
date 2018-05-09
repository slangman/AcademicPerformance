package ru.innopolis.stc9.db.dao;

import ru.innopolis.stc9.pojo.User;

import java.sql.SQLException;

public interface UserDAO {
    public User getUserById(int id) throws SQLException;
    public User getUserByLogin(String login) throws SQLException;
    public boolean addUser(User user) throws SQLException;
    public boolean updateUser(int id, User newUser) throws SQLException;
    public boolean deleteUser(int id) throws SQLException;
}

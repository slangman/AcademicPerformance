package ru.innopolis.stc9.db.dao;

import ru.innopolis.stc9.pojo.User;

import java.sql.SQLException;

public interface UserDAO {
    public User getUserById(int id);
    public User getUserByLogin(String login);
    public boolean addUser(User user);
    public boolean updateUser(int id, User newUser);
    public void deleteUser(int id);
}

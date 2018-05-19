package ru.innopolis.stc9.service;

import ru.innopolis.stc9.db.dao.AdminDAO;
import ru.innopolis.stc9.db.dao.UserDAOImpl;
import ru.innopolis.stc9.pojo.Admin;
import ru.innopolis.stc9.pojo.Student;
import ru.innopolis.stc9.pojo.Teacher;
import ru.innopolis.stc9.pojo.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class AdminService {

    private String oldPasswordMessage = null;
    private String newPasswordMessage = null;

    private UserDAOImpl userDAO = new UserDAOImpl();
    private AdminDAO adminDao = new AdminDAO();
    private UserService userService = new UserService();

    public String getOldPasswordMessage() {
        return oldPasswordMessage;
    }

    public String getNewPasswordMessage() {
        return newPasswordMessage;
    }

    public boolean addUser(User newUser) {
        return userDAO.addUser(newUser);
    }

    public List<User> getUsersList() {
        return adminDao.getUsersList();
    }

    public String getUpdateMessage(String msg) {
        String result = null;
        if (msg != null && !msg.isEmpty()) {
            if (msg.equals("updated")) {
                result = "User updated successfully.";
            }
            if (msg.equals("updateError")) {
                result = "Failed updating user.";
            }
        }
        return result;
    }

    public boolean updateUser(String login, String newFirstName, String newLastName) {
        User user = userDAO.getUserByLogin(login);
        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
        return userDAO.updateUser(userDAO.getUserId(login), user);
    }

    public boolean updatePassword(HttpServletRequest req) {
        boolean result = false;
        String login = req.getParameter("editLogin");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String repeatNewPassword = req.getParameter("repeatNewPassword");
        boolean checkOldPassword=false;
        if (oldPassword!=null && !oldPassword.isEmpty()) {
            String passwordHash = getPasswordHash(login);
            checkOldPassword = userService.checkPassword(oldPassword, passwordHash);
        }
        if (checkOldPassword) {
            if (passwordIsSecure(newPassword)) {
                if (newPassword.equals(repeatNewPassword)) {
                    userDAO.updatePassword(login, newPassword);
                    result=true;
                } else {
                    System.out.println("Passwords are not equal");
                    newPasswordMessage = "Passwords are not equal";
                }
            } else {
                System.out.println("New password must contain at least 3 characters.");
                newPasswordMessage = "New password must contain at least 3 characters.";
            }
        } else {
            System.out.println("Incorrect old password");
            oldPasswordMessage = "Incorrect old password";
        }
        return result;
    }



    private boolean passwordIsSecure(String password) {
        boolean result=false;
        if (password.length() > 2) {
            result=true;
        }
        return result;
    }

    private String getPasswordHash(String login) {
        return userDAO.getUserByLogin(login).getPassword();
    }




}

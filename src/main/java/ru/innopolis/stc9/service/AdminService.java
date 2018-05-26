package ru.innopolis.stc9.service;

import ru.innopolis.stc9.db.dao.AdminDAO;
import ru.innopolis.stc9.db.dao.CourseDAOImpl;
import ru.innopolis.stc9.db.dao.UserDAOImpl;
import ru.innopolis.stc9.pojo.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class AdminService {

    private String oldPasswordMessage = null;
    private String newPasswordMessage = null;

    private UserDAOImpl userDAO = new UserDAOImpl();
    private AdminDAO adminDao = new AdminDAO();
    private CourseDAOImpl courseDAO = new CourseDAOImpl();
    private UserService userService = new UserService();

    public Object getSessionAttribute (HttpServletRequest req , String attribute) {
        Object result=null;
        if (req.getSession().getAttribute(attribute)!=null) {
            result = req.getSession().getAttribute(attribute);
            req.getSession().setAttribute(attribute, null);
        }
        return result;
    }

    public String addOrEditCourse(HttpServletRequest req) {
        String result="";
        String operation = (String) getSessionAttribute(req, "operation");
        int courseId = (Integer)getSessionAttribute(req, "courseId");
        String courseName = req.getParameter("courseName");
        String courseDescription = req.getParameter("courseDescription");
        Course course = new Course();
        course.setName(courseName);
        course.setDescription(courseDescription);
        //TODO хардкод, поменять!
        course.setTeacherId(100);
        if (operation.equals("new-course")) {
            courseDAO.addCourse(course);
            result = "course-added";
        }
        if (operation.equals("edit-existing")) {
            courseDAO.updateCourse(courseId, course);
            result = "updated";
        }
        return ("?operation=" + operation + "&course-id=" + courseId + "&msg=" + result);
    }

    public List<Course> getCourses() {
        return courseDAO.getCoursesList();
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

    public String getErrorMessage(String err) {
        String result = null;
        if (err != null && !err.isEmpty()) {
            if (err.equals("passwords-not-equal")) {
                result = "Passwords are not equal.";
            }
            if (err.equals("insecure-pass")) {
                result = "New password must contain at least 3 characters.";
            }
            if (err.equals("incorrect-old-pass")) {
                result = "Incorrect old password.";
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

    public String updatePassword(HttpServletRequest req) {
        String result = null;
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
                } else {
                    result = "passwords-not-equal";
                }
            } else {
                result="insecure-pass";
            }
        } else {
            result="incorrect-old-pass";
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

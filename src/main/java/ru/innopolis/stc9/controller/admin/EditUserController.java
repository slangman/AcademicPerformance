package ru.innopolis.stc9.controller.admin;

import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.AdminService;
import ru.innopolis.stc9.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserController extends HttpServlet {
    UserService userService = new UserService();
    AdminService adminService = new AdminService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("user-login");
        String msg = req.getParameter("msg");
        String err = req.getParameter("err");
        if (login != null && !login.isEmpty()) {
            User user = userService.getUserByLogin(login);
            req.setAttribute("user", user);
        }
        if (msg != null && !msg.isEmpty()) {
            String updateMessage = adminService.getUpdateMessage(msg);
            req.setAttribute("updateMessage", updateMessage);
        }
        if (err !=null && !err.isEmpty()) {
            String errorMessage = adminService.getErrorMessage(err);
            req.setAttribute("errorMessage", errorMessage);
        }

        req.getRequestDispatcher("/admin-tools/edit-user.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fName = req.getParameter("firstName");
        String lName = req.getParameter("lastName");
        String editLogin = req.getParameter("editLogin");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String repeatNewPassword = req.getParameter("repeatNewPassword");
        String msg;
        String err="";
        boolean updateSuccess = adminService.updateUser(editLogin, fName, lName);
        String updatePasswordResult = adminService.updatePassword(req);
        if (updateSuccess && updatePasswordResult==null) {
            msg = "updated";
        } else {
            msg = "updateError";
        }
        if (updatePasswordResult!=null) {
            err = updatePasswordResult;
        }
        System.out.println(updatePasswordResult);
        resp.sendRedirect(req.getContextPath() + "?user-login=" + editLogin + "&msg=" + msg + "&err=" + err);

    }
}

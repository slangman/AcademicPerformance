import ru.innopolis.stc9.db.dao.UserDAO;
import ru.innopolis.stc9.db.dao.UserDAOImpl;
import ru.innopolis.stc9.pojo.Admin;
import ru.innopolis.stc9.pojo.Student;
import ru.innopolis.stc9.pojo.User;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserDAOImpl userDao = new UserDAOImpl();
        Student tempUser = new Student();
        tempUser.setLogin("admin1");
        tempUser.setPassword("password");
        System.out.println(userDao.addUser(tempUser));
    }
}

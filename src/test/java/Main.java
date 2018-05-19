import ru.innopolis.stc9.db.dao.UserDAO;
import ru.innopolis.stc9.db.dao.UserDAOImpl;
import ru.innopolis.stc9.pojo.Admin;
import ru.innopolis.stc9.pojo.Student;
import ru.innopolis.stc9.pojo.Teacher;
import ru.innopolis.stc9.pojo.User;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserDAOImpl userDao = new UserDAOImpl();
        System.out.println(userDao.updatePassword("ronweasley", "123"));
    }
}

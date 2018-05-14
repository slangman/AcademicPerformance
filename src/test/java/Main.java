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
        Teacher teacher = new Teacher("Rubeus", "Hagrid");
        teacher.setLogin("teacherrh");
        teacher.setPassword("123");
        System.out.println(userDao.addUser(teacher));
    }
}

package dbTests.daoTests;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import ru.innopolis.stc9.db.connection.ConnectionManager;
import ru.innopolis.stc9.db.dao.UserDAO;
import ru.innopolis.stc9.db.dao.UserDAOImpl;
import ru.innopolis.stc9.pojo.Admin;
import ru.innopolis.stc9.pojo.Student;
import ru.innopolis.stc9.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserDAOTest {
   /* public static void main(String[] args) throws SQLException {
        UserDAOImpl userDAO = new UserDAOImpl();
        Admin me = new Admin();
        me.setFirstName("I am");
        me.setLastName("a God");
        me.setLogin("hustle");
        me.setPassword("365308");
        userDAO.addUser(me);
    }*/

    private UserDAOImpl userDao;

    @Before
    public void before() throws SQLException {
        userDao = new UserDAOImpl();
    }

    @Test
    public void getUserByIdTest() throws SQLException {
        User result = null;
        result = userDao.getUserById(2);
        assertTrue(result!=null);
    }

    @Test
    public void getUserByLoginTest() throws SQLException {
        User result = null;
        result = userDao.getUserByLogin("dongenaro");
        assertTrue(result!=null);
        assertTrue(result.getLogin()!=null);
        assertTrue(result.getPassword()!=null);
    }

    @Test
    public void getUserIdTest() throws SQLException {
        int result = -1;
        result = userDao.getUserId("dongenaro");
        assertTrue(result!=(-1));
    }

    @Test
    public void addAndDeleteUserTest() throws SQLException {
        Student tempUser = new Student();
        tempUser.setLogin("tempUser");
        tempUser.setPassword("password");
        assertTrue(userDao.addUser(tempUser));
        int id = userDao.getUserId("tempuser");
        assertTrue(userDao.deleteUser(id));
    }

    @Test (expected = SQLException.class)
    public void addExistingUserTest() throws SQLException {
        Student tempUser1 = new Student();
        tempUser1.setLogin("tempUser1");
        tempUser1.setPassword("password");
        userDao.addUser(tempUser1);
        userDao.addUser(tempUser1);
    }

    @Test
    public void updateUserTest() throws SQLException {
        Student tempUser1 = new Student();
        tempUser1.setLogin("tempUser1");
        tempUser1.setPassword("password");
        userDao.addUser(tempUser1);
        tempUser1.setPassword("newPassword");
        assertTrue(userDao.updateUser(userDao.getUserId("tempUser1"), tempUser1));

    }

    @After
    public void after() throws SQLException {
        if (userDao.getUserByLogin("tempUser1")!=null) {
            userDao.deleteUser(userDao.getUserId("tempUser1"));
        }
    }


}

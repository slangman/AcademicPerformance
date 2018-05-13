package dbTests.daoTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.innopolis.stc9.db.dao.UserDAOImpl;;
import ru.innopolis.stc9.pojo.Student;
import ru.innopolis.stc9.pojo.User;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserDAOTest {
   /*public static void main(String[] args) {
        UserDAOImpl userDAO = new UserDAOImpl();
        User user = userDAO.getUserByLogin("dongenaro");
        System.out.println(user.getClass().getName());
    }*/

    private UserDAOImpl userDao;

    @Before
    public void before() {
        userDao = new UserDAOImpl();
    }

    @Test
    public void getUserByIdTest() {
        User result = null;
        result = userDao.getUserById(2);
        assertTrue(result!=null);
    }

    @Test
    public void getUserByLoginTest() {
        User result = null;
        result = userDao.getUserByLogin("dongenaro");
        assertTrue(result!=null);
        assertTrue(result.getLogin()!=null);
        assertTrue(result.getPassword()!=null);
    }

    @Test
    public void getUserIdTest() {
        int result = -1;
        result = userDao.getUserId("dongenaro");
        assertTrue(result!=(-1));
    }

    @Test
    public void addAndDeleteUserTest() {
        Student tempUser = new Student("tempUser", "password");
        assertTrue(userDao.addUser(tempUser));
        int id = userDao.getUserId("tempuser");
        userDao.deleteUser(id);
        assertTrue(userDao.getUserById(id)==null);
    }

    @Test
    public void addExistingUserTest() throws SQLException {
        Student tempUser1 = new Student("tempUser1", "password");
        userDao.addUser(tempUser1);
        assertFalse(userDao.addUser(tempUser1));
    }

    @Test
    public void updateUserTest() {
        Student tempUser1 = new Student("tempUser1", "password");
        userDao.addUser(tempUser1);
        tempUser1.setPassword("newPassword");
        assertTrue(userDao.updateUser(userDao.getUserId("tempUser1"), tempUser1));

    }

    @After
    public void after() {
        if (userDao.getUserByLogin("tempUser1")!=null) {
            userDao.deleteUser(userDao.getUserId("tempUser1"));
        }
    }


}

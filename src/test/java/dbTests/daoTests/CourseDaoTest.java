package dbTests.daoTests;

import org.junit.Before;
import org.junit.Test;
import ru.innopolis.stc9.db.dao.CourseDAOImpl;
import ru.innopolis.stc9.pojo.Course;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class CourseDaoTest {
    private CourseDAOImpl courseDao;

    @Before
    public void before() {
        courseDao = new CourseDAOImpl();
    }

    @Test
    public void getCourseByIdTest() throws SQLException {
        Course course = courseDao.getCourseById(3);
        assertTrue(course!=null);
    }

}

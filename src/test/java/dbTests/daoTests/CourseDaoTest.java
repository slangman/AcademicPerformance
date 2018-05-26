package dbTests.daoTests;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc9.db.dao.CourseDAOImpl;
import ru.innopolis.stc9.pojo.Course;

import java.util.List;


public class CourseDaoTest {
    private CourseDAOImpl courseDao;

    @BeforeClass
    public void beforeClass() {
        courseDao=new CourseDAOImpl();
    }

    @Before
    public void before() {
        Course testCourse = new Course();
        testCourse.setName("testName");
        testCourse.setDescription("testDescription");
        testCourse.setTeacherId(100);
        courseDao.addCourse(testCourse);
    }

    @Test
    public void getCoursesListTest() {
        List<Course> coursesList = courseDao.getCoursesList();
        Assert.assertTrue(coursesList.size() > 0);
    }

    @Test
    public void getCourseByIdTest() {
        Course course = courseDao.getCourseById(3);
        Assert.assertTrue(course!=null);
    }

    @Test
    public void getCourseByName() {
        Course course = courseDao.getCourseByName("testCourse");
        Assert.assertTrue(course!=null);
    }

    @After
    public void after() {
        courseDao.getCourseByName("testName");
    }

}

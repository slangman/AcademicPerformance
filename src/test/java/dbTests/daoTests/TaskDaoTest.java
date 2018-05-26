package dbTests.daoTests;

import org.junit.Before;
import org.junit.Test;
import ru.innopolis.stc9.db.dao.TaskDAOImpl;
import ru.innopolis.stc9.pojo.Task;

import static org.junit.Assert.*;

public class TaskDaoTest {
    private TaskDAOImpl taskDao;

    @Before
    public void before() {
        taskDao = new TaskDAOImpl();
    }

    @Test
    public void getTaskByIdTest() {
        Task task = taskDao.getTaskById(1);
        assertTrue(task!=null);
    }
}

package ru.innopolis.stc9.service;

import ru.innopolis.stc9.db.dao.TaskDAOImpl;
import ru.innopolis.stc9.pojo.Task;

public class TaskService {
    private TaskDAOImpl taskDao = new TaskDAOImpl();

    public Task getTask(int taskId) {
        return taskDao.getTaskById(taskId);
    }
}

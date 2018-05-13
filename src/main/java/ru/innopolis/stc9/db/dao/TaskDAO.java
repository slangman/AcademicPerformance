package ru.innopolis.stc9.db.dao;

import ru.innopolis.stc9.pojo.Task;

import java.sql.SQLException;

public interface TaskDAO {
    public Task getTaskById(int id);
    public boolean addTask(Task task);
    public boolean updateTask(int id, Task newTask);
    public void deleteTaskById(int id);
}

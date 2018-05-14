package ru.innopolis.stc9.db.dao;

import ru.innopolis.stc9.pojo.Course;

import java.sql.SQLException;

public interface CourseDAO {
    public Course getCourseByName(String name);
    public Course getCourseById(int id);
    public boolean addCourse (Course course);
    public boolean updateCourse(int id, Course newCourse);
    public void deleteCourseById(int id);
}

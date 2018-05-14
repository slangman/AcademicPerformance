package ru.innopolis.stc9.service;


import ru.innopolis.stc9.db.dao.CourseDAOImpl;

public class CourseService {
    private CourseDAOImpl courseDao = new CourseDAOImpl();

    public String getCourseName(int courseId) {
        String result=null;
        if (courseId>0) {
            result=courseDao.getCourseById(courseId).getName();
        }
        return result;
    }

    //TODO: отрефакторить
    public int getCourseId(String courseName) {
        return courseDao.getCourseId(courseName);
    }


}

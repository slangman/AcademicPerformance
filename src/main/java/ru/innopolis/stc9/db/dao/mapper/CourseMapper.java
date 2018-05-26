package ru.innopolis.stc9.db.dao.mapper;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.pojo.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CourseMapper {
    private static final Logger logger = Logger.getLogger("defaultLog");

    public static List<Course> getCourseListFromResultSet(ResultSet resultSet) {
        List<Course> result = new ArrayList<Course>();
        if (resultSet!=null) {
            try {
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setId(resultSet.getInt("id"));
                    course.setName(resultSet.getString("name"));
                    course.setDescription(resultSet.getString("description"));
                    course.setTeacherId(resultSet.getInt("teacherid"));
                    result.add(course);
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return result;
    }
}

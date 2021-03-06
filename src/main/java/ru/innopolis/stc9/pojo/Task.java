package ru.innopolis.stc9.pojo;

public class Task {
    private int id;
    private String name;
    private String description;
    private int courseId;

    public Task(String name, String description, int courseId) {
        this.name = name;
        this.description = description;
        this.courseId = courseId;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id=id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}

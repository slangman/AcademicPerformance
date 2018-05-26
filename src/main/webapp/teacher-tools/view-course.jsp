<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="ru.innopolis.stc9.pojo.Task" %>
<%@ page import="java.util.List" %>
<%
    int courseId = (Integer)request.getAttribute("courseId");
    String courseName = (String)request.getAttribute("courseName");
    List<Task> tasks = (List<Task>)request.getAttribute("tasks");
    HashMap<String, Double> students = (HashMap<String, Double>)request.getAttribute("students");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header.jsp"%>
<div class = "container menu">
    <div class="row">
        <div class="col-md-12">
            <a href = "/teacher/dashboard">Dashboard Home</a> -> View course
        </div>
    </div>
</div>
<div class = "container">
    <div class = "row">
        <div class="col-md-12">
            Course: <h2><%=courseName%></h2>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <b>Course tasks</b>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            Task name
        </div>
        <div class="col-md-8">
            Task description
        </div>
        <div class="col-md-1">

        </div>
    </div>
    <%for (Task task : tasks) {%>
    <div class="row">
        <div class="col-md-3">
            <%=task.getName()%>
        </div>
        <div class="col-md-8">
            <%=task.getDescription()%>
        </div>
        <div class="col-md-1">
            <a href="${pageContext.request.contextPath}/teacher/edit-task?operation=edit-existing&courseid=<%=courseId%>&task-id=<%=task.getId()%>"><img src="${pageContext.request.contextPath}/images/ic_edit_black_18dp.png"></a>
            <a href="#"><img src="/images/ic_delete_18pt.png"></a>
        </div>
    </div>
    <%}%>
    <div class="row">
        <div class="col-md-2">
            <input class="btn btn-primary" type="button" value="New task" onclick="location.href = '${pageContext.request.contextPath}/teacher/edit-task?operation=new-task&courseid=<%=courseId%>';">
        </div>
    </div>

    <div class = "row">
        <div class="col-md-12 students-list">
            <b>Students list</b>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4">
            Name
        </div>
        <div class="col-md-2">
            Completed
        </div>
        <div class="col-md-2">
            Average grade
        </div>
    </div>
    <%for (Map.Entry<String, Double> entry : students.entrySet()) {%>
    <div class="row">
        <div class="col-md-4">
            <%=entry.getKey()%>
        </div>
        <div class="col-md-2">

        </div>
        <div class="col-md-2">
            <%=entry.getValue()%>
        </div>
        <div class="col-md-1">
            <a href="#"><img src="/images/ic_delete_18pt.png"></a>
        </div>
    </div>
    <%}%>
    <div class="row">
        <div class="col-md-12">
            <input class="btn btn-primary" type="button" value="Add students">
        </div>
    </div>
</div>
<%@ include file="/footer.jsp"%>

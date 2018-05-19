<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%
    String courseName = (String)request.getAttribute("courseName");
    HashMap<String, String> tasks = (HashMap<String, String>)request.getAttribute("tasks");
    HashMap<String, Double> students = (HashMap<String, Double>)request.getAttribute("students");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header.jsp"%>
<div class = "container">
    <div class = "row">
        <div class="col-md-12">
            Course: <h2><%=courseName%></h2>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            Course tasks:
        </div>
    </div>
    <div class="row">
        <div class="col-md-4">
            Task name
        </div>
        <div class="col-md-8">
            Task description
        </div>
    </div>
    <%for (Map.Entry<String, String> entry : tasks.entrySet()) {%>
    <div class="row">
        <div class="col-md-4">
            <%=entry.getKey()%>
        </div>
        <div class="col-md-8">
            <%=entry.getValue()%>
        </div>
    </div>
    <%}%>

    <div class = "row">
        <div class="col-md-12">
           Students list:
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
    </div>
    <%}%>
</div>
<%@ include file="/footer.jsp"%>

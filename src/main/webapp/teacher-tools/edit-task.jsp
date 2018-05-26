<%@ page import="ru.innopolis.stc9.pojo.Task" %>
<%
    Task task = (Task)request.getAttribute("task");
    String courseid = request.getParameter("courseid");
%>

<%@ include file="/header.jsp"%>
<div class = "container menu">
    <div class="row">
        <div class="col-md-12">
            <a href = "/teacher/dashboard">Dashboard Home</a> -> <a href="/teacher/view-course?courseid=<%=courseid%>">View course</a> -> Edit task
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            Task edit form
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <form action="/teacher/edit-task" method="post">
                <div class="form-group row">
                    <label for="taskName" class="col-sm-3 col-form-label">Name</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="taskName" placeholder="Enter task name" name="taskName"
                               <%if (task!=null) {%>
                               value="<%=task.getName()%>"
                               <%} else {%>
                                value=""
                               <%}%>
                        >
                    </div>
                </div>
                <div class="form-group row">
                    <label for="taskDescription" class="col-sm-3 col-form-label">Description</label>
                    <div class="col-sm-9">
                        <textarea class="form-control" id="taskDescription" name = "taskDescription" rows="3"><%=task != null ? task.getDescription() : ""%></textarea>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Save</button>
            </form>
        </div>
        <div class="col-md-6">

        </div>
    </div>
</div>
<%@include file="/footer.jsp"%>
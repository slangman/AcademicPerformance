<%@ page import="ru.innopolis.stc9.pojo.Course" %>
<%
    Course course = (Course)request.getAttribute("course");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header.jsp"%>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h2>Course edit form</h2>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <form action="${pageContext.request.contextPath}/admin/edit-course" method="post">
                <div class="form-group row">
                    <label for="courseName" class="col-sm-3 col-form-label">Name</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="courseName" placeholder="Enter course name" name="courseName"
                            <%if (course!=null) {%>
                               value="<%=course.getName()%>"
                            <%} else {%>
                               value=""
                            <%}%>
                        >
                    </div>
                </div>
                <div class="form-group row">
                    <label for="courseDescription" class="col-sm-3 col-form-label">Description</label>
                    <div class="col-sm-9">
                        <textarea class="form-control" id="courseDescription" name = "courseDescription" rows="3"><%=course != null ? course.getDescription() : ""%></textarea>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Save</button>
            </form>
        </div>
    </div>
</div>
<%@ include file="/footer.jsp"%>
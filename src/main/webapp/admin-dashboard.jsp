<%@ page import="java.util.ArrayList" %>
<%@ page import="ru.innopolis.stc9.pojo.User" %>
<%@ page import="ru.innopolis.stc9.pojo.Course" %>
<%@ page import="java.util.List" %>
<%
    List<User> users = (ArrayList<User>)request.getAttribute("users");
    List<Course> courses = (ArrayList<Course>)request.getAttribute("courses");
    String helloMessage = (String)request.getAttribute("helloMessage");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>
<div class = "container">
    <div class = "row">
        <div class="col-md-12">
            <h2>Admin dashboard</h2>
            <p><%=helloMessage%></p>
        </div>
    </div>
    <div class="row">

    </div>
    <div class="row">
        <div class="col-md-12">
            <b>Existing users:</b>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">Login</div>
        <div class="col-md-3">First Name</div>
        <div class="col-md-3">Last Name</div>
        <div class="col-md-3">Role</div>
    </div>
    <%for (User user : users) {%>
    <div class = "row">
        <div class="col-md-3">
            <%=user.getLogin()%>
        </div>
        <div class="col-md-3">
            <%=user.getFirstName()%>
        </div>
        <div class="col-md-3">
            <%=user.getLastName()%>
        </div>
        <div class="col-md-2">
            <%=user.getClass().getSimpleName()%>
        </div>
        <div class="col-md-1">
            <a href="${pageContext.request.contextPath}/admin/edit-user?user-login=<%=user.getLogin()%>"><img src="${pageContext.request.contextPath}/images/ic_edit_black_18dp.png"></a>
            <a href="#"><img src="/images/ic_delete_18pt.png"></a>
        </div>

    </div>
    <%}%>
    <div class="row">
        <div class="col-md-12">
            <div class="col-md-12">
                <input class="btn btn-primary" type="button" value="New user">
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <b>Existing courses:</b>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">Name</div>
        <div class="col-md-3">Description</div>
        <div class="col-md-3">Teacher</div>
    </div>
    <%for (Course course : courses) {%>
    <div class="row">
        <div class="col-md-3"><%=course.getName()%></div>
        <div class="col-md-3"><%=course.getDescription()%></div>
        <div class="col-md-3"><%=course.getTeacherId()%></div>
        <div class="col-md-1"><a href="${pageContext.request.contextPath}/admin/edit-course?operation=edit-existing&course-id=<%=course.getId()%>"><img src="${pageContext.request.contextPath}/images/ic_edit_black_18dp.png"></a></div>
    </div>
    <%}%>
</div>
<%@ include file="footer.jsp"%>
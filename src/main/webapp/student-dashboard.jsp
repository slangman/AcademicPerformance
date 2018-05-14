<%@ page import="ru.innopolis.stc9.pojo.Course" %>
<%@ page import="java.util.ArrayList" %>
<%
    ArrayList<String> courses = (ArrayList<String>)request.getAttribute("courses");
    String helloMessage = (String)request.getAttribute("helloMessage");
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>
<div class = "container">
    <div class = "row">
        <div class="col-md-12">
            <h2>Student dashboard</h2>
            <p><%=helloMessage%></p>

        </div>
    </div>
    <div class="row">
       <div class="col-md-12">
           Courses you are taking:
       </div>
    </div>
    <%for (String courseName : courses) {%>
    <div class = "row">
        <div class="col-md-12">
            <a href="${pageContext.request.contextPath}/student/view-grades?course-name=<%=courseName%>"><%=courseName%></a>
        </div>
    </div>
    <%}%>
    <div class="row">
        <p></p>
    </div>
</div>
<%@ include file="footer.jsp"%>

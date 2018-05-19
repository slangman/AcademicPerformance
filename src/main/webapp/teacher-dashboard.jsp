<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%
    HashMap<Integer, String> courses = (HashMap<Integer, String>)request.getAttribute("courses");
    String helloMessage = (String)request.getAttribute("helloMessage");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>
<div class = "container">
    <div class = "row">
        <div class="col-md-12">
            <h2>Teacher Dashboard</h2>
            <p><%=helloMessage%></p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            Courses you are teaching:
        </div>
    </div>
    <%for (Map.Entry<Integer, String> entry : courses.entrySet()) {%>
    <div class = "row">
        <div class="col-md-12">
            <a href="${pageContext.request.contextPath}/teacher/view-course?courseid=<%=entry.getKey()%>"><%=entry.getValue()%></a>
        </div>
    </div>
    <%}%>
</div>
<%@ include file="footer.jsp"%>

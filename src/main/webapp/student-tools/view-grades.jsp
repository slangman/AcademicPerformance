<%@ page import="java.util.Map" %>
<%String courseName = (String)request.getAttribute("courseName");%>
<%Map<String, Integer> grades = (Map<String, Integer>)request.getAttribute("grades");%>
<%@ include file="/header.jsp"%>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            Course: <h2><%=courseName%></h2>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">Task</div>
        <div class="col-md-3">Grade</div>
    </div>
    <%for (Map.Entry<String, Integer> entry : grades.entrySet()) {%>
    <div class="row">
        <div class="col-md-3">
            <%=entry.getKey()%>
        </div>
        <div class="col-md-3">
            <%=entry.getValue()%>
        </div>
    </div>
    <%}%>
</div>
<%@ include file="/footer.jsp"%>
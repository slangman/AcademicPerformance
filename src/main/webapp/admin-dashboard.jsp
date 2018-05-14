<%@ page import="java.util.ArrayList" %>
<%@ page import="ru.innopolis.stc9.pojo.User" %>
<%
    ArrayList<User> users = (ArrayList<User>)request.getAttribute("users");
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
        <div class="col-md-12">
            Users:
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
</div>
<%@ include file="footer.jsp"%>
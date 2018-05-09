<%--
  Created by IntelliJ IDEA.
  User: Rikki
  Date: 07.05.2018
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Логин/пароль</title>
</head>
<body>
<%=request.getAttribute("message") + "<br>"%>
<!--%
    Long currentTime = System.currentTimeMillis();
    String timeMessage = "Current Time: " + currentTime;
%-->
<!--%=timeMessage%-->
<%=("authError".equals(request.getParameter("errorMsg"))) ? "Wrong username or password" : ""%><BR>
<%=("noAuth".equals(request.getParameter("errorMsg"))) ? "Please authorise first" : ""%><BR>
<form action="${pageContext.request.contextPath}/login" method="post">
    <input type="text" value="user" name="userName"><BR>
    <input type="password" value="password" name="userPassword"><BR>
    <input type="submit" value="OK">
</form>
</body>
</html>

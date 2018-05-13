<%--
  Created by IntelliJ IDEA.
  User: Rikki
  Date: 07.05.2018
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!--%=request.getAttribute("message") + "<br>"%-->
<!--%
    Long currentTime = System.currentTimeMillis();
    String timeMessage = "Current Time: " + currentTime;
%-->
<!--%=timeMessage%-->
<div class="back">
    <div class="div-center">
        <div class="content">
            <h3>Login</h3>
            <hr />
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" id="loginInput" placeholder="Username" name="userName">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" id="passwordInput" placeholder="Password" name="userPassword">
                </div>
                <button type="submit" class="btn btn-primary">Login</button>
            </form>
            <%=("authError".equals(request.getParameter("errorMsg"))) ? "Wrong username or password" : ""%><BR>
            <%=("noAuth".equals(request.getParameter("errorMsg"))) ? "Please authorise first" : ""%><BR>
        </div>
    </div>
</div>

<!--div class = "container">
    <div class = "row">
        <div class="col-md-4 col-md-offset-4">
            <%=("authError".equals(request.getParameter("errorMsg"))) ? "Wrong username or password" : ""%><BR>
            <%=("noAuth".equals(request.getParameter("errorMsg"))) ? "Please authorise first" : ""%><BR>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <input type="text" value="user" name="userName"><BR>
                <input type="password" value="password" name="userPassword"><BR>
                <input type="submit" value="OK">
            </form>
        </div>
    </div>
<div/-->
<!--%@ include file="templates/footer.jsp" %-->
</body>
</html>

<%@ page import="ru.innopolis.stc9.pojo.User" %>
<%User user = (User)request.getAttribute("user");%>
<%@ include file="/header.jsp"%>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h2>User edit form</h2>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <form action="${pageContext.request.contextPath}/admin/edit-user" method="post">
                    <div class="form-group">
                        <label>Login</label><input type="text" class="form-control" id="login" placeholder="Username" name="userName" value = "<%=user.getLogin()%>" readonly>
                    </div>
                    <div class="form-group">
                        <label>First name</label><input type="text" class="form-control" id="firstName" placeholder="First Name" name="firstName" value="<%=user.getFirstName()%>">
                    </div>
                    <div class="form-group">
                        <label>Last name</label><input type="text" class="form-control" id="lastName" placeholder="Last Name" name="lastName" value="<%=user.getLastName()%>">
                    </div>
                    <div class="form-group">
                        <label>User's role</label><br>
                        <input type="radio" name="role" value="Admin">Admin<br>
                        <input type="radio" name="role" value="Teacher">Teacher<br>
                        <input type="radio" name="role" value="Student">Student<br>
                    </div>
                    <button type="submit" class="btn btn-primary">Save</button>
                </form>
            </div>
            <div class="col-md-6"></div>
        </div>
    </div>
<%@ include file="/footer.jsp"%>
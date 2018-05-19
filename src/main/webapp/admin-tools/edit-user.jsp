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
                    <div class="form-group row">
                        <label for="editLogin" class= "col-sm-3 col-form-label">Login</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="editLogin" placeholder="Username" name="editLogin" value = "<%=user.getLogin()%>" readonly>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="firstName" class = "col-sm-3 col-form-label">First name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="firstName" placeholder="First Name" name="firstName" value="<%=user.getFirstName()%>">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="lastName" class="col-sm-3 col-form-label">Last name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="lastName" placeholder="Last Name" name="lastName" value="<%=user.getLastName()%>">
                        </div>
                    </div>
                    <b>Change password</b>
                    <div class="form-group">
                        <label>Old password</label><input type="password" class="form-control" id="oldPassword" placeholder="Old password" name="oldPassword">

                        <%if (request.getAttribute("oldPasswordMessage")!=null) {
                            String oldPasswordMessage = (String)request.getAttribute("oldPasswordMessage");
                            %>
                        <p><%=oldPasswordMessage%></p>
                        <%}%>

                        <label>New passord</label><input type="password" class="form-control" id="newPassword" placeholder="New password" name="newPassword">
                        <%if (request.getAttribute("newPasswordMessage")!=null) {
                            String newPasswordMessage = (String)request.getAttribute("newPasswordMessage");
                        %>
                        <p><%=newPasswordMessage%></p>
                        <%}%>
                        <label>Repeat new passord</label><input type="password" class="form-control" id="repeatNewPassword" placeholder="Repeat new password" name="repeatNewPassword">
                    </div>
                    <button type="submit" class="btn btn-primary">Save</button>
                </form>
            </div>
            <div class="col-md-6">
            </div>
        </div>
        <%if (request.getAttribute("updateMessage")!=null) {
            String message = (String)request.getAttribute("updateMessage");
        %>
        <div class = "row">
            <div class="col-md-12 message-box">
                <%=message%>
            </div>
        </div>
        <%}%>
        <div class="row">
            <div class="col-md-12">
                <%=request.getAttribute("oldPasswordMessage")%>
                <%=request.getAttribute("newPasswordMessage")%>
            </div>
        </div>
    </div>
<%@ include file="/footer.jsp"%>
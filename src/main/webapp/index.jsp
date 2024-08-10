<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.groupd.beans.User" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>

<%
    if (session != null) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String role = user.getRole();
            String contextPath = request.getContextPath();
            String redirectPage = contextPath + "/index.jsp"; // Default to index.jsp if role not found

            // Define role-specific home pages
            String patientHome = contextPath + "/patients/patientHome.jsp";
            String doctorHome = contextPath + "/doctors/doctorHome.jsp";
            String staffHome = contextPath + "/staffs/staffHome.jsp";

            // Redirect based on user role
            if ("patient".equalsIgnoreCase(role)) {
                redirectPage = patientHome;
            } else if ("doctor".equalsIgnoreCase(role)) {
                redirectPage = doctorHome;
            } else if ("staff".equalsIgnoreCase(role)) {
                redirectPage = staffHome;
            }

            response.sendRedirect(redirectPage);
            return; // Ensure no further code executes in this JSP
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet"> <!-- Font Awesome -->
    <link href="css/master.css" rel="stylesheet">
    <link href="css/home.css" rel="stylesheet">
    <style>
        .input-group-text {
            cursor: pointer;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark">
    <a class="navbar-brand" href="#">HMS</a>
</nav>

<div class="container-fluid login-container">
    <div class="row w-100">
        <div class="col-md-6 login-image d-none d-md-flex">
            <!-- Full screen image on larger screens -->
        </div>
        <div class="col-md-6 login-form">
            <form action="login" method="post" class="w-100">
                <h2 class="text-center">Login</h2>
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" id="username" name="username" placeholder="Enter username">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <div class="input-group">
                        <input type="password" class="form-control" id="password" name="password" placeholder="Enter password">
                        <div class="input-group-append">
                            <span class="input-group-text" id="togglePassword">
                                <i class="fas fa-eye"></i> <!-- Eye icon -->
                            </span>
                        </div>
                    </div>
                </div>
                <!-- Error message container -->
                <%
                    String error = (String) request.getAttribute("error");
                    if (error != null && !error.isEmpty()) {
                %>
                <div class="alert alert-danger" role="alert">
                    <%= error %>
                </div>
                <%
                    }
                %>
                <button type="submit" class="btn btn-primary btn-block">Login</button>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    // Toggle password visibility
    document.getElementById('togglePassword').addEventListener('click', function () {
        const passwordField = document.getElementById('password');
        const eyeIcon = this.querySelector('i');

        if (passwordField.type === 'password') {
            passwordField.type = 'text';
            eyeIcon.classList.remove('fa-eye');
            eyeIcon.classList.add('fa-eye-slash');
        } else {
            passwordField.type = 'password';
            eyeIcon.classList.remove('fa-eye-slash');
            eyeIcon.classList.add('fa-eye');
        }
    });
</script>
</body>
</html>

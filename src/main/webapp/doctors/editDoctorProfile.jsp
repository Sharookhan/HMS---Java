<%@ page import="com.groupd.beans.Doctor" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doctor Profile</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/master.css" rel="stylesheet">
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<div class="container mt-4">
    <h2>Doctor Profile</h2>
    <%
        Doctor doctor = (Doctor) request.getAttribute("doctor");
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
    <div class="alert alert-warning" role="alert">
        <%= message %>
    </div>
    <%
    } else if (doctor != null) {
    %>
    <div class="card">
        <div class="card-body">
            <h5 class="card-title"><%= doctor.getFirstName() %> <%= doctor.getLastName() %></h5>
            <p class="card-text">ID: <%= doctor.getDoctorId() %></p>
            <p class="card-text">Specialization: <%= doctor.getSpecialization() %></p>
            <p class="card-text">Phone Number: <%= doctor.getPhoneNumber() %></p>
            <p class="card-text">Email: <%= doctor.getEmail() %></p>
            <p class="card-text">Profile: <%= doctor.getProfile() %></p>
            <a href="<%= request.getContextPath() %>/appointments.jsp" class="btn btn-primary">Back to Appointments</a>
        </div>
    </div>
    <%
    } else {
    %>
    <div class="alert alert-warning" role="alert">
        No doctor profile available.
    </div>
    <%
        }
    %>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

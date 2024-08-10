<%@ page import="com.groupd.beans.Patient" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View/Edit Patient</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/master.css" rel="stylesheet">
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<div class="container mt-4 form-container">
    <!-- Display error/success messages -->
    <%
        String message = (String) request.getAttribute("message");
        String alertClass = (String) request.getAttribute("alertClass");
        if (message != null && alertClass != null) {
    %>
    <div class="alert <%= alertClass %>" role="alert">
        <%= message %>
    </div>
    <%
        }
    %>

    <!-- Search Form -->
    <h2>Search Patient</h2>
    <form action="${pageContext.request.contextPath}/viewEditPatient" method="get" class="mb-4">
        <div class="form-group">
            <label for="patientIdSearch">Patient ID:</label>
            <input type="text" id="patientIdSearch" name="patientId" class="form-control"
                   value="<%= request.getParameter("patientId") != null ? request.getParameter("patientId") : "" %>" required>
        </div>
        <button type="submit" class="btn btn-primary">Search</button>
    </form>

    <!-- Display/Edit Patient Form -->
    <%
        Patient patient = (Patient) request.getAttribute("patient");
        if (patient != null) {
    %>
    <h2>Edit Patient Details</h2>
    <form action="${pageContext.request.contextPath}/viewEditPatient" method="post" class="mb-4">
        <input type="hidden" name="patientId" value="<%= patient.getPatientId() %>">
        <div class="form-group">
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName" class="form-control" value="<%= patient.getFirstName() %>" required>
        </div>
        <div class="form-group">
            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" name="lastName" class="form-control" value="<%= patient.getLastName() %>" required>
        </div>
        <div class="form-group">
            <label for="dateOfBirth">Date of Birth:</label>
            <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" value="<%= patient.getDateOfBirth() %>" required>
        </div>
        <div class="form-group">
            <label for="gender">Gender:</label>
            <input type="text" id="gender" name="gender" class="form-control" value="<%= patient.getGender() %>" required>
        </div>
        <div class="form-group">
            <label for="phoneNumber">Phone Number:</label>
            <input type="text" id="phoneNumber" name="phoneNumber" class="form-control" value="<%= patient.getPhoneNumber() %>" required>
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" class="form-control" value="<%= patient.getEmail() %>" required>
        </div>
        <div class="form-group">
            <label for="address">Address:</label>
            <input type="text" id="address" name="address" class="form-control" value="<%= patient.getAddress() %>" required>
        </div>
        <button type="submit" class="btn btn-primary">Update</button>
    </form>
    <%
        }
    %>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

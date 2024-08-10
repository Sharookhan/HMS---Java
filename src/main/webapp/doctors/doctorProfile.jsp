<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.groupd.beans.Doctor" %>
<%@ page import="com.groupd.dao.DoctorDAO" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HMS - Doctor Home</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/master.css" rel="stylesheet">
</head>
<body>
<%@ include file="../common/navbar.jsp" %>

<%
    DoctorDAO doctorDAO = new DoctorDAO();
    Doctor doctor = doctorDAO.getDoctor(user.getId());
%>

<div class="container mt-4">
    <h1>Doctor's Profile</h1>

    <div class="container">
        <table class="table">
            <tr>
                <th>Doctor ID</th>
                <td><%= doctor.getDoctorId() %></td>
            </tr>
            <tr>
                <th>First Name</th>
                <td><%= doctor.getFirstName() %></td>
            </tr>
            <tr>
                <th>Last Name</th>
                <td><%= doctor.getLastName() %></td>
            </tr>
            <tr>
                <th>Specialization</th>
                <td><%= doctor.getSpecialization() %></td>
            </tr>
            <tr>
                <th>Phone Number</th>
                <td><%= doctor.getPhoneNumber() %></td>
            </tr>
            <tr>
                <th>Email</th>
                <td><%= doctor.getEmail() %></td>
            </tr>
            <tr>
                <th>Profile</th>
                <td><%= doctor.getProfile() %></td>
            </tr>
        </table>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

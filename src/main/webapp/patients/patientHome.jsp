<%@ page import="com.groupd.dao.AppointmentDAO" %>
<%@ page import="com.groupd.beans.Appointment" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HMS - Patients Home</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/master.css" rel="stylesheet">
</head>
<body>
<%@ include file="../common/navbar.jsp" %>

<%
    AppointmentDAO appointmentDAO = new AppointmentDAO();

    Appointment appointment = appointmentDAO.getPatientAppointment(user.getId());

%>

<div class="container mt-4">
    <h1>Welcome, <%= user.getFirstName() %></h1>

    <div class="container">
        <table class="table">
            <tr>
                <th>Appointment ID</th>
                <td><%= appointment.getAppointmentId() %></td>
            </tr>
            <tr>
                <th>Appointment Date</th>
                <td><%= appointment.getAppointmentDate() %></td>
            </tr>
            <tr>
                <th>Appointment Time</th>
                <td><%= appointment.getAppointmentTime() %></td>
            </tr>
            <tr>
                <th>Feedback</th>
                <td><%= appointment.getFeedback() %></td>
            </tr>
        </table>
    </div>

</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

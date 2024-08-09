<%@ page import="com.groupd.beans.Appointment" %>
<%@ page import="com.groupd.dao.AppointmentDAO" %>
<%@ page import="com.groupd.beans.Patient" %>
<%@ page import="com.groupd.dao.PatientDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Appointment</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/master.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark">
    <a class="navbar-brand" href="#">HMS</a>
</nav>

<div class="container mt-4">
    <h2>Appointment Details</h2>
    <%
        String message = (String) request.getAttribute("message");
        String alertClass = (String) request.getAttribute("alertClass");
        if (message != null) {
    %>
    <div class="alert <%= alertClass %>" role="alert">
        <%= message %>
    </div>
    <%
    } else {
        Appointment appointment = (Appointment) request.getAttribute("appointment");
        if (appointment != null) {
    %>
    <table class="table table-bordered">
        <tbody>
        <tr>
            <th>Appointment ID</th>
            <td><%= appointment.getAppointmentId() %></td>
        </tr>
        <tr>
            <th>Patient ID</th>
            <td><%= appointment.getPatientId() %></td>
        </tr>
        <tr>
            <th>Doctor ID</th>
            <td><%= appointment.getDoctorId() %></td>
        </tr>
        <tr>
            <th>Date</th>
            <td><%= appointment.getAppointmentDate() %></td>
        </tr>
        <tr>
            <th>Time</th>
            <td><%= appointment.getAppointmentTime() %></td>
        </tr>
        <tr>
            <th>Feedback</th>
            <td><%= appointment.getFeedback() %></td>
        </tr>
        </tbody>
    </table>
    <a href="manageAppointments" class="btn btn-secondary">Back to Appointments</a>
    <%
    } else {
    %>
    <p>No appointment details available.</p>
    <%
            }
        }
    %>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Appointments</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/master.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark">
    <a class="navbar-brand" href="#">HMS</a>
</nav>

<div class="container mt-4">
    <h2>Manage Appointments</h2>
    <%
        String message = (String) request.getAttribute("message");
        String alertClass = (String) request.getAttribute("alertClass");
        if (message != null) {
    %>
    <div class="alert <%= alertClass %>" role="alert">
        <%= message %>
    </div>
    <%
        }
    %>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Appointment ID</th>
            <th>Patient ID</th>
            <th>Date</th>
            <th>Time</th>
            <th>Feedback</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
            if (appointments != null && !appointments.isEmpty()) {
                for (Appointment appointment : appointments) {
        %>
        <tr>
            <td><%= appointment.getAppointmentId() %></td>
            <td><%= appointment.getPatientId() %></td>
            <td><%= appointment.getAppointmentDate() %></td>
            <td><%= appointment.getAppointmentTime() %></td>
            <td><%= appointment.getFeedback() %></td>
            <td>
                <a href="viewAppointment?appointmentId=<%= appointment.getAppointmentId() %>" class="btn btn-primary">View</a>
                <a href="editAppointment?appointmentId=<%= appointment.getAppointmentId() %>" class="btn btn-warning">Edit</a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="6">No appointments found.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.groupd.dao.AppointmentDAO" %>
<%@ page import="com.groupd.dao.PatientDAO" %>
<%@ page import="com.groupd.beans.Appointment" %>
<%@ page import="com.groupd.beans.Patient" %>
<%@ page import="com.groupd.beans.User" %>
<%@ page import="java.util.List" %>
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
<div class="container mt-4 form-container">

    <!-- Display pending appointments -->
    <h2>Pending Appointments</h2>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Patient Name</th>
            <th>Date</th>
            <th>Time</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            // Check if the 'user' object is already defined in the session
            User sessionUser = (User) session.getAttribute("user");
            if (sessionUser != null && "doctor".equals(sessionUser.getRole())) {
                String doctorId = sessionUser.getId(); // Get doctor ID from User object
                AppointmentDAO ap = new AppointmentDAO();
                PatientDAO pao = new PatientDAO();
                List<Appointment> appointments = ap.getPendingAppointmentsByDoctor(doctorId);

                if (appointments != null && !appointments.isEmpty()) {
                    for (Appointment appointment : appointments) {
                        Patient patient = pao.getPatient(appointment.getPatientId());
        %>
        <tr>
            <td><%= patient.getFirstName() %> <%= patient.getLastName() %></td>
            <td><%= appointment.getAppointmentDate() %></td>
            <td><%= appointment.getAppointmentTime() %></td>
            <td>
                <a href="<%= request.getContextPath() %>/viewAppointment?appointmentId=<%= appointment.getAppointmentId() %>">View</a> |
                <a href="<%= request.getContextPath() %>/editAppointment?appointmentId=<%= appointment.getAppointmentId() %>">Edit</a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="4">No pending appointments found.</td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="4">Doctor ID is not available or you are not logged in as a doctor.</td>
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

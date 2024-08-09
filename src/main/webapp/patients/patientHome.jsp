<%@ page import="com.groupd.dao.AppointmentDAO" %>
<%@ page import="com.groupd.dao.DoctorDAO" %>
<%@ page import="com.groupd.beans.Appointment" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Time" %>
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
    // Retrieve all appointments for the current patient
    AppointmentDAO appointmentDAO = new AppointmentDAO();
    DoctorDAO doctorDAO = new DoctorDAO();
    List<Appointment> appointments = appointmentDAO.getAllAppointmentsByPatientId(user.getId());
    List<Appointment> pendingAppointments = new ArrayList<>();
    List<Appointment> completedAppointments = new ArrayList<>();

    // Define a date formatter for time
    SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");

    // Categorize appointments based on feedback
    for (Appointment appointment : appointments) {
        if (appointment.getFeedback() == null || appointment.getFeedback().trim().isEmpty()) {
            pendingAppointments.add(appointment);
        } else {
            completedAppointments.add(appointment);
        }
    }
%>

<div class="container mt-4">

    <!-- Pending Appointments Section -->
    <h2>Pending Appointments</h2>
    <div class="table-responsive">
        <table class="table table-bordered">
            <thead class="thead-dark" >
            <tr>
                <th class="th-black">Appointment ID</th>
                <th class="th-black">Date</th>
                <th class="th-black">Time</th>
                <th class="th-black">Doctor</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (pendingAppointments.isEmpty()) {
            %>
            <tr>
                <td colspan="4" class="text-center">No pending appointments.</td>
            </tr>
            <%
            } else {
                for (Appointment appointment : pendingAppointments) {
                    String formattedTime = timeFormatter.format(appointment.getAppointmentTime());
                    String doctorName = doctorDAO.getDoctorNameById(appointment.getDoctorId());
            %>
            <tr>
                <td><%= appointment.getAppointmentId() %></td>
                <td><%= appointment.getAppointmentDate() %></td>
                <td><%= formattedTime %></td>
                <td><%= doctorName %></td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>

    <!-- Completed Appointments Section -->
    <h2>Completed Appointments</h2>
    <div class="table-responsive">
        <table class="table table-bordered">
            <thead class="thead-dark">
            <tr>
                <th class="th-black">Appointment ID</th>
                <th class="th-black">Date</th>
                <th class="th-black">Time</th>
                <th class="th-black">Feedback</th>
                <th class="th-black">Doctor</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (completedAppointments.isEmpty()) {
            %>
            <tr>
                <td colspan="5" class="text-center">No completed appointments.</td>
            </tr>
            <%
            } else {
                for (Appointment appointment : completedAppointments) {
                    String formattedTime = timeFormatter.format(appointment.getAppointmentTime());
                    String doctorName = doctorDAO.getDoctorNameById(appointment.getDoctorId());
            %>
            <tr>
                <td><%= appointment.getAppointmentId() %></td>
                <td><%= appointment.getAppointmentDate() %></td>
                <td><%= formattedTime %></td>
                <td><%= appointment.getFeedback() %></td>
                <td><%= doctorName %></td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

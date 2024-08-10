<%@ page import="com.groupd.beans.Appointment" %>
<%@ page import="com.groupd.dao.AppointmentDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Appointment</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/master.css" rel="stylesheet">
</head>
<body>
<%@ include file="../common/navbar.jsp" %>

<div class="container mt-4">
    <h2>Edit Appointment</h2>
    <%
        String appointmentIdStr = request.getParameter("appointmentId");
        int appointmentId = 0;
        Appointment appointment = null;

        if (appointmentIdStr != null && !appointmentIdStr.isEmpty()) {
            appointmentId = Integer.parseInt(appointmentIdStr);

            AppointmentDAO appointmentDAO = new AppointmentDAO();
            try {
                appointment = appointmentDAO.getAppointment(appointmentId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (appointment != null) {
    %>
    <form action="<%= request.getContextPath() %>/updateAppointment" method="post">
        <input type="hidden" name="appointmentId" value="<%= appointment.getAppointmentId() %>" />
        <div class="form-group">
            <label for="patientId">Patient ID</label>
            <input type="text" class="form-control" id="patientId" name="patientId" value="<%= appointment.getPatientId() %>" required disabled>
        </div>
        <div class="form-group">
            <label for="doctorId">Doctor ID</label>
            <input type="text" class="form-control" id="doctorId" name="doctorId" value="<%= appointment.getDoctorId() %>" required>
        </div>
        <div class="form-group">
            <label for="appointmentDate">Date</label>
            <input type="date" class="form-control" id="appointmentDate" name="appointmentDate" value="<%= appointment.getAppointmentDate().toLocalDate() %>" required>
        </div>
        <div class="form-group">
            <label for="appointmentTime">Time</label>
            <input type="time" class="form-control" id="appointmentTime" name="appointmentTime" value="<%= appointment.getAppointmentTime().toLocalTime() %>" required>
        </div>
        <div class="form-group">
            <label for="feedback">Feedback</label>
            <textarea class="form-control" id="feedback" name="feedback"><%= appointment.getFeedback() %></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Update Appointment</button>
        <a href="<%= request.getContextPath() %>/doctors/doctorHome.jsp" class="btn btn-secondary">Back to Appointments</a>
    </form>
    <%
    } else {
    %>
    <p>Appointment not found.</p>
    <a href="<%= request.getContextPath() %>/doctors/doctorHome.jsp" class="btn btn-secondary">Back to Appointments</a>
    <%
        }
    %>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

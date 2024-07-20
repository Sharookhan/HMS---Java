<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.groupd.dao.DoctorDAO" %>
<%@ page import="com.groupd.dao.PatientDAO" %>
<%@ page import="com.groupd.beans.Doctor" %>
<%@ page import="com.groupd.beans.Patient" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HMS - Staff Home</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/master.css" rel="stylesheet">
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<div class="container mt-4 form-container">
    <!-- Display error/success messages -->
    <c:if test="${not empty message}">
        <div class="alert ${alertClass}" role="alert">
                ${message}
        </div>
    </c:if>

    <!-- Appointment Booking Form -->
    <h2>Book an Appointment</h2>
    <form action="${pageContext.request.contextPath}/bookAppointment" method="post">
        <div class="form-group">
            <label for="patientId">Patient</label>
            <select class="form-control" id="patientId" name="patientId" required>
                <%
                    PatientDAO patientDAO = new PatientDAO();
                    List<Patient> patients = null;
                    try {
                        patients = patientDAO.getAllPatients();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (patients != null) {
                        for (Patient patient : patients) {
                            String id = patient.getPatientId();
                            String name = patient.getFirstName() + " " + patient.getLastName();
                %>
                <option value="<%= id %>"><%= name %></option>
                <%
                        }
                    }
                %>
            </select>
        </div>
        <div class="form-group">
            <label for="doctorId">Doctor</label>
            <select class="form-control" id="doctorId" name="doctorId" required>
                <%
                    DoctorDAO doctorDAO = new DoctorDAO();
                    List<Doctor> doctors = null;
                    try {
                        doctors = doctorDAO.getAllDoctors();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (doctors != null) {
                        for (Doctor doctor : doctors) {
                            String id = doctor.getDoctorId();
                            String name = doctor.getFirstName() + " " + doctor.getLastName();
                %>
                <option value="<%= id %>"><%= name %></option>
                <%
                        }
                    }
                %>
            </select>
        </div>
        <div class="form-group">
            <label for="appointmentDate">Appointment Date</label>
            <input type="date" class="form-control" id="appointmentDate" name="appointmentDate" required>
        </div>
        <div class="form-group">
            <label for="appointmentTime">Appointment Time</label>
            <input type="time" class="form-control" id="appointmentTime" name="appointmentTime" required>
        </div>
        <button type="submit" class="btn btn-primary">Book Appointment</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

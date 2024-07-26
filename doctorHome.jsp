<%@ page import="java.util.List" %>
<%@ page import="com.groupd.beans.Appointment" %>

<!DOCTYPE html>
<html>
<head>
    <title>Doctor Home</title>
</head>
<body>
    <h1>Welcome, Doctor</h1>

    <h2>Appointments</h2>
    <table border="1">
        <thead>
            <tr>
                <th>Patient ID</th>
                <th>Date</th>
                <th>Time</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
                if (appointments != null) {
                    for (Appointment appointment : appointments) {
            %>
            <tr>
                <td><%= appointment.getPatientId() %></td>
                <td><%= appointment.getAppointmentDate() %></td>
                <td><%= appointment.getAppointmentTime() %></td>
                <td>
                    <a href="<%= request.getContextPath() %>/viewAppointment?appointmentId=<%= appointment.getAppointmentId() %>">View</a> |
                    <a href="<%= request.getContextPath() %>/editAppointment?appointmentId=<%= appointment.getAppointmentId() %>">Edit</a>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
</body>
</html>

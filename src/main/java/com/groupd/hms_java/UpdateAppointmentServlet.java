package com.groupd.hms_java;

import com.groupd.beans.Appointment;
import com.groupd.dao.AppointmentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UpdateAppointmentServlet", urlPatterns = {"/updateAppointment"})
public class UpdateAppointmentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appointmentIdStr = request.getParameter("appointmentId");
        String appointmentDate = request.getParameter("appointmentDate");
        String appointmentTime = request.getParameter("appointmentTime");
        String feedback = request.getParameter("feedback");
        String patientId = request.getParameter("patientId");
        String doctorId = request.getParameter("doctorId");

        if (appointmentIdStr == null || appointmentDate == null || appointmentTime == null || patientId == null || doctorId == null) {
            request.setAttribute("message", "All fields are required.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/editAppointment.jsp?appointmentId=" + appointmentIdStr).forward(request, response);
            return;
        }

        int appointmentId = Integer.parseInt(appointmentIdStr);
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentId);
        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);
        appointment.setAppointmentDate(java.sql.Date.valueOf(appointmentDate));

        try {
            if (!appointmentTime.matches("\\d{2}:\\d{2}(:\\d{2})?")) {
                throw new IllegalArgumentException("Invalid time format.");
            }
            if (appointmentTime.length() == 5) {
                appointmentTime += ":00";
            }
            appointment.setAppointmentTime(java.sql.Time.valueOf(appointmentTime));
        } catch (IllegalArgumentException e) {
            request.setAttribute("message", "Invalid time format. Use HH:MM or HH:MM:SS.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/editAppointment.jsp?appointmentId=" + appointmentIdStr).forward(request, response);
            return;
        }

        appointment.setFeedback(feedback);

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        try {
            appointmentDAO.updateAppointment(appointment);
            request.setAttribute("message", "Appointment updated successfully!");
            request.setAttribute("alertClass", "alert-success");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while updating the appointment.");
            request.setAttribute("alertClass", "alert-danger");
        }

        response.sendRedirect(request.getContextPath() + "/viewAppointment?appointmentId=" + appointmentId);
    }
}

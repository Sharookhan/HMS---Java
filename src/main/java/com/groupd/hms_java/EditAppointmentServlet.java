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

@WebServlet(name = "EditAppointmentServlet", urlPatterns = {"/editAppointment"})
public class EditAppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appointmentIdStr = request.getParameter("appointmentId");
        if (appointmentIdStr == null || appointmentIdStr.isEmpty()) {
            request.setAttribute("message", "Appointment ID is required.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/doctors/viewAppointments.jsp").forward(request, response);
            return;
        }

        int appointmentId;
        try {
            appointmentId = Integer.parseInt(appointmentIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid Appointment ID.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/doctors/viewAppointments.jsp").forward(request, response);
            return;
        }

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        Appointment appointment;
        try {
            appointment = appointmentDAO.getAppointment(appointmentId);
        } catch (SQLException e) {
            request.setAttribute("message", "An error occurred while retrieving the appointment.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/doctors/viewAppointments.jsp").forward(request, response);
            return;
        }

        if (appointment != null) {
            request.setAttribute("appointment", appointment);
            request.getRequestDispatcher("/doctors/editAppointment.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "Appointment not found.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/doctors/viewAppointments.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int appointmentId;
        try {
            appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid Appointment ID.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/doctors/viewAppointments.jsp").forward(request, response);
            return;
        }

        String patientId = request.getParameter("patientId");
        String doctorId = request.getParameter("doctorId");
        String appointmentDateStr = request.getParameter("appointmentDate");
        String appointmentTimeStr = request.getParameter("appointmentTime");
        String feedback = request.getParameter("feedback");

        if (patientId == null || doctorId == null || appointmentDateStr == null || appointmentTimeStr == null) {
            request.setAttribute("message", "All fields are required.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/doctors/editAppointment.jsp").forward(request, response);
            return;
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentId);
        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);
        appointment.setAppointmentDate(java.sql.Date.valueOf(appointmentDateStr));

        // Handle time formatting
        try {
            if (!appointmentTimeStr.matches("\\d{2}:\\d{2}(:\\d{2})?")) {
                throw new IllegalArgumentException("Invalid time format.");
            }
            if (appointmentTimeStr.length() == 5) {
                appointmentTimeStr += ":00";
            }
            appointment.setAppointmentTime(java.sql.Time.valueOf(appointmentTimeStr));
        } catch (IllegalArgumentException e) {
            request.setAttribute("message", "Invalid time format. Use HH:MM or HH:MM:SS.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/doctors/editAppointment.jsp").forward(request, response);
            return;
        }
        appointment.setFeedback(feedback);

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        try {
            appointmentDAO.updateAppointment(appointment);
            request.setAttribute("message", "Appointment updated successfully!");
            request.setAttribute("alertClass", "alert-success");
        } catch (SQLException e) {
            request.setAttribute("message", "An error occurred while updating the appointment.");
            request.setAttribute("alertClass", "alert-danger");
        }
        request.getRequestDispatcher("/doctors/viewAppointments.jsp").forward(request, response);
    }
}

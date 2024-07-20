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

@WebServlet(name = "BookAppointmentServlet", urlPatterns = {"/bookAppointment"})
public class BookAppointmentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientId = request.getParameter("patientId");
        String doctorId = request.getParameter("doctorId");
        String appointmentDate = request.getParameter("appointmentDate");
        String appointmentTime = request.getParameter("appointmentTime");

        // Validate input
        if (patientId == null || doctorId == null || appointmentDate == null || appointmentTime == null) {
            request.setAttribute("message", "All fields are required.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/staffs/staffHome.jsp").forward(request, response);
            return;
        }

        Appointment appointment = new Appointment();
        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);
        appointment.setAppointmentDate(java.sql.Date.valueOf(appointmentDate));

        // Handle time formatting
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
            request.getRequestDispatcher("/staffs/staffHome.jsp").forward(request, response);
            return;
        }

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        boolean isSuccess = false;
        try {
            isSuccess = appointmentDAO.addAppointment(appointment);
        } catch (SQLException e) {
            request.setAttribute("message", "An error occurred while booking the appointment.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/staffs/staffHome.jsp").forward(request, response);
            return;
        }

        if (isSuccess) {
            request.setAttribute("message", "Appointment booked successfully!");
            request.setAttribute("alertClass", "alert-success");
        } else {
            request.setAttribute("message", "Failed to book the appointment.");
            request.setAttribute("alertClass", "alert-danger");
        }

        request.getRequestDispatcher("/staffs/staffHome.jsp").forward(request, response);
    }
}

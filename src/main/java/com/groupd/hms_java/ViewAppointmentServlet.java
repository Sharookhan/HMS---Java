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

@WebServlet(name = "ViewAppointmentServlet", urlPatterns = {"/viewAppointment"})
public class ViewAppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appointmentIdParam = request.getParameter("appointmentId");
        if (appointmentIdParam == null || appointmentIdParam.isEmpty()) {
            request.setAttribute("message", "Appointment ID is required.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/doctors/doctorHome.jsp").forward(request, response);
            return;
        }

        int appointmentId;
        try {
            appointmentId = Integer.parseInt(appointmentIdParam);
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid appointment ID.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/doctors/doctorHome.jsp").forward(request, response);
            return;
        }

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        Appointment appointment;
        try {
            appointment = appointmentDAO.getAppointment(appointmentId);
        } catch (SQLException e) {
            request.setAttribute("message", "An error occurred while retrieving the appointment.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/doctors/doctorHome.jsp").forward(request, response);
            return;
        }

        if (appointment == null) {
            request.setAttribute("message", "Appointment not found.");
            request.setAttribute("alertClass", "alert-info");
        }

        request.setAttribute("appointment", appointment);
        request.getRequestDispatcher("/doctors/viewAppointment.jsp").forward(request, response);
    }
}

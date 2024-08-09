package com.groupd.hms_java;

import com.groupd.dao.AppointmentDAO;
import com.groupd.beans.Appointment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "FetchAppointmentsServlet", urlPatterns = {"/fetchAppointments"})
public class FetchAppointmentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        try {
            List<Appointment> appointments = appointmentDAO.getAllAppointments();
            request.setAttribute("appointments", appointments);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while fetching appointments.");
        }
        request.getRequestDispatcher("/doctors/doctorHome.jsp").forward(request, response);
    }
}

package com.groupd.hms_java;

import com.groupd.beans.Doctor;
import com.groupd.dao.DoctorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DoctorProfileServlet", urlPatterns = {"/doctors/doctorProfile"})
public class DoctorProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String doctorId = (String) session.getAttribute("doctorId");

        if (doctorId == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        DoctorDAO doctorDAO = new DoctorDAO();
        try {
            Doctor doctor = doctorDAO.getDoctor(doctorId);
            if (doctor != null) {
                request.setAttribute("doctor", doctor);
                request.getRequestDispatcher("/doctors/doctorProfile.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "No profile information available.");
                request.getRequestDispatcher("/doctors/doctorProfile.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while retrieving the profile.");
            request.getRequestDispatcher("/doctors/doctorProfile.jsp").forward(request, response);
        }
    }
}


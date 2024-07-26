package com.groupd.hms_java;

import com.groupd.dao.DoctorDAO;
import com.groupd.beans.Doctor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "EditDoctorProfileServlet", urlPatterns = {"/editDoctorProfile"})
public class EditDoctorProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String doctorId = request.getParameter("doctorId");
        DoctorDAO doctorDAO = new DoctorDAO();
        try {
            Doctor doctor = doctorDAO.getDoctor(doctorId);
            request.setAttribute("doctor", doctor);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while fetching doctor details.");
        }
        request.getRequestDispatcher("/doctors/editDoctorProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String doctorId = request.getParameter("doctorId");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String specialty = request.getParameter("specialty");
        String email = request.getParameter("email");

        Doctor doctor = new Doctor();
        doctor.setDoctorId(doctorId);
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setSpecialty(specialty);
        doctor.setEmail(email);

        DoctorDAO doctorDAO = new DoctorDAO();
        try {
            doctorDAO.updateDoctor(doctor);
            request.setAttribute("message", "Profile updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while updating the profile.");
        }

        request.getRequestDispatcher("/doctors/editDoctorProfile.jsp").forward(request, response);
    }
}

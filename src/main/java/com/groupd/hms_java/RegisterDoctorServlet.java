package com.groupd.hms_java;

import com.groupd.beans.Doctor;
import com.groupd.dao.DoctorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RegisterDoctorServlet", urlPatterns = {"/registerDoctor"})
public class RegisterDoctorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String specialization = request.getParameter("specialization");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String profile = request.getParameter("profile");
        String password = request.getParameter("password");

        // Validate input
        if (firstName == null || lastName == null || specialization == null || phoneNumber == null || email == null || profile == null || password == null) {
            request.setAttribute("message", "All fields are required.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/staffs/staffRegisterDoctor.jsp").forward(request, response);
            return;
        }

        // Generate Doctor ID
        DoctorDAO doctorDAO = new DoctorDAO();
        String doctorId;
        try {
            doctorId = doctorDAO.getNextDoctorId();
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while generating the doctor ID.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/staffs/staffRegisterDoctor.jsp").forward(request, response);
            return;
        }

        // Create a new Doctor object
        Doctor doctor = new Doctor();
        doctor.setDoctorId(doctorId);
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setSpecialization(specialization);
        doctor.setPhoneNumber(phoneNumber);
        doctor.setEmail(email);
        doctor.setProfile(profile);
        doctor.setPassword(password); // Ensure you hash the password in a real application

        // Use DoctorDAO to add the doctor to the database
        try {
            doctorDAO.addDoctor(doctor);
            request.setAttribute("message", "Doctor registered successfully.");
            request.setAttribute("alertClass", "alert-success");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error registering doctor.");
            request.setAttribute("alertClass", "alert-danger");
        }

        // Forward back to the registration page with a message
        request.getRequestDispatcher("/staffs/staffRegisterDoctor.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to the registration form
        request.getRequestDispatcher("/staffs/staffRegisterDoctor.jsp").forward(request, response);
    }
}

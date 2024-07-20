package com.groupd.hms_java;

import com.groupd.dao.PatientDAO;
import com.groupd.beans.Patient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RegisterPatientServlet", urlPatterns = {"/registerPatient"})
public class RegisterPatientServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String gender = request.getParameter("gender");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String password = request.getParameter("password");

        // Validate input
        if (firstName == null || lastName == null || dateOfBirth == null || gender == null || phoneNumber == null || email == null || address == null || password == null) {
            request.setAttribute("message", "All fields are required.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/staffs/staffRegisterPatient.jsp").forward(request, response);
            return;
        }

        PatientDAO patientDAO = new PatientDAO();
        String patientId;
        try {
            patientId = patientDAO.getNextPatientId();
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while generating the patient ID.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/staffs/staffRegisterPatient.jsp").forward(request, response);
            return;
        }

        Patient patient = new Patient();
        patient.setPatientId(patientId);
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setDateOfBirth(java.sql.Date.valueOf(dateOfBirth));
        patient.setGender(gender);
        patient.setPhoneNumber(phoneNumber);
        patient.setEmail(email);
        patient.setAddress(address);
        patient.setPassword(password); // Make sure to hash the password before storing it in a real application

        try {
            patientDAO.addPatient(patient);
            request.setAttribute("message", "Patient registered successfully!");
            request.setAttribute("alertClass", "alert-success");
            request.setAttribute("patientId", patientId); // Pass the generated ID to the JSP
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while registering the patient.");
            request.setAttribute("alertClass", "alert-danger");
        }

        request.getRequestDispatcher("/staffs/staffRegisterPatient.jsp").forward(request, response);
    }
}

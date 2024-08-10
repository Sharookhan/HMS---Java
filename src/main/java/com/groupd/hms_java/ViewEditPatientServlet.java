package com.groupd.hms_java;

import com.groupd.beans.Patient;
import com.groupd.dao.PatientDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "ViewEditPatientServlet", urlPatterns = {"/viewEditPatient"})
public class ViewEditPatientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientId = request.getParameter("patientId");
        PatientDAO patientDAO = new PatientDAO();

        if (patientId == null || patientId.trim().isEmpty()) {
            request.setAttribute("message", "Patient ID is required.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("staffs/staffViewEditPatient.jsp").forward(request, response);
            return;
        }

        try {
            Patient patient = patientDAO.getPatient(patientId);
            if (patient != null) {
                request.setAttribute("patient", patient);
            } else {
                request.setAttribute("message", "Patient not found.");
                request.setAttribute("alertClass", "alert-danger");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Failed to retrieve patient details.");
            request.setAttribute("alertClass", "alert-danger");
        }

        request.getRequestDispatcher("staffs/staffViewEditPatient.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientId = request.getParameter("patientId");

        if (patientId == null || patientId.trim().isEmpty()) {
            request.setAttribute("message", "Patient ID is required.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("staffs/staffViewEditPatient.jsp").forward(request, response);
            return;
        }

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        Date dateOfBirth = Date.valueOf(request.getParameter("dateOfBirth"));
        String gender = request.getParameter("gender");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        PatientDAO patientDAO = new PatientDAO();
        Patient patient = new Patient();
        patient.setPatientId(patientId);
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setDateOfBirth(dateOfBirth);
        patient.setGender(gender);
        patient.setPhoneNumber(phoneNumber);
        patient.setEmail(email);
        patient.setAddress(address);

        try {
            patientDAO.updatePatient(patient);
            request.setAttribute("message", "Patient details updated successfully!");
            request.setAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Failed to update patient details.");
            request.setAttribute("alertClass", "alert-danger");
        }

        // Retrieve updated patient details to display in the form
        try {
            patient = patientDAO.getPatient(patientId);
            request.setAttribute("patient", patient);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Failed to retrieve updated patient details.");
            request.setAttribute("alertClass", "alert-danger");
        }

        request.getRequestDispatcher("staffs/staffViewEditPatient.jsp").forward(request, response);
    }
}

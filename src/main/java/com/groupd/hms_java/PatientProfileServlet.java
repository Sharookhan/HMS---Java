package com.groupd.hms_java;

import com.groupd.utils.DataSourceUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

@WebServlet(name = "patientProfileServlet", value = "/patient-profile")
public class PatientProfileServlet extends HttpServlet {

    @Resource(name = "jdbc/hms_db")
    private DataSource dataSource;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        String patientId = request.getParameter("id");
        if (patientId == null || patientId.isEmpty()) {
            out.println("<h2>No patient ID provided.</h2>");
            out.println("</body></html>");
            out.close();
            return;
        }

        try (Connection conn = DataSourceUtils.getConnection()) {
            String query = "SELECT first_name, last_name, date_of_birth, gender, phone_number, address FROM Patients WHERE patient_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, patientId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String firstName = rs.getString("first_name");
                        String lastName = rs.getString("last_name");
                        LocalDate dob = LocalDate.parse(rs.getString("date_of_birth"));
                        String gender = rs.getString("gender");
                        String phoneNumber = rs.getString("phone_number");
                        String address = rs.getString("address");

                        int age = Period.between(dob, LocalDate.now()).getYears();

                        out.println("<h2>Patient Profile:</h2>");
                        out.println("<p>First Name: " + firstName + "</p>");
                        out.println("<p>Last Name: " + lastName + "</p>");
                        out.println("<p>Age: " + age + "</p>");
                        out.println("<p>Gender: " + gender + "</p>");
                        out.println("<p>Phone Number: " + phoneNumber + "</p>");
                        out.println("<p>Address: " + address + "</p>");
                    } else {
                        out.println("<h2>No patient found with ID: " + patientId + "</h2>");
                    }
                }
            }
        } catch (SQLException e) {
            out.println("Error: " + e.getMessage());
            e.printStackTrace(out);
        }

        out.println("</body></html>");
        out.close();
    }
}

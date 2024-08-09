package com.groupd.hms_java;

import com.groupd.beans.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.groupd.utils.DataSourceUtils;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null) {
            request.setAttribute("error", "Invalid input");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        // Determine the user type based on the first letter of the username
        char userTypeInitial = username.toUpperCase().charAt(0);
        String userType = null;
        String query = "";
        String redirectPage = "";
        String role = "";

        try (Connection conn = DataSourceUtils.getConnection()) {
            switch (userTypeInitial) {
                case 'D':
                    userType = "doctor";
                    query = "SELECT doctor_id, first_name, last_name FROM Doctors WHERE doctor_id = ? AND password = ?";
                    redirectPage = "/doctors/doctorHome.jsp";
                    role = "doctor";
                    break;
                case 'S':
                    userType = "staff";
                    query = "SELECT staff_id, first_name, last_name FROM Staff WHERE staff_id = ? AND password = ?";
                    redirectPage = "/staffs/staffHome.jsp";
                    role = "staff";
                    break;
                case 'P':
                    userType = "patient";
                    query = "SELECT patient_id, first_name, last_name FROM Patients WHERE patient_id = ? AND password = ?";
                    redirectPage = "/patients/patientHome.jsp";
                    role = "patient";
                    break;
                default:
                    request.setAttribute("error", "Invalid user type");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                    return;
            }

            // Validate credentials
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Create User object
                        User user = new User();
                        user.setId(username);
                        user.setRole(role);
                        user.setFirstName(rs.getString("first_name"));
                        user.setLastName(rs.getString("last_name"));
                        // Store User object in session
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                        response.sendRedirect(request.getContextPath() + redirectPage);
                    } else {
                        request.setAttribute("error", "Invalid credentials");
                        request.getRequestDispatcher("/index.jsp").forward(request, response);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}

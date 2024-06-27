package com.groupd.hms_java;
import com.groupd.utils.DataSourceUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    @Resource(name = "jdbc/hms_db")
    private DataSource dataSource;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        // Get datasource using JNDI lookup
        try {
            // Establish database connection
            try (Connection conn = DataSourceUtils.getConnection();) {
                // Execute query to fetch first names from 'patients' table
                String query = "SELECT first_name FROM patients";
                try (PreparedStatement pstmt = conn.prepareStatement(query);
                     ResultSet rs = pstmt.executeQuery()) {

                    out.println("<h2>First Names of Patients:</h2>");
                    // Print all first names
                    while (rs.next()) {
                        out.println(rs.getString("first_name") + "<br>");
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

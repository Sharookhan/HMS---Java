package com.groupd.hms_java;

import com.groupd.dao.FeedbackDAO;
import com.groupd.beans.Feedback;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "FeedbackServlet", urlPatterns = {"/submitFeedback"})
public class FeedbackServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String doctorId = request.getParameter("doctorId");
        String feedbackText = request.getParameter("feedback");

        if (doctorId == null || feedbackText == null) {
            request.setAttribute("message", "All fields are required.");
            request.setAttribute("alertClass", "alert-danger");
            request.getRequestDispatcher("/doctors/doctorHome.jsp").forward(request, response);
            return;
        }

        Feedback feedback = new Feedback();
        feedback.setDoctorId(doctorId);
        feedback.setFeedbackText(feedbackText);

        FeedbackDAO feedbackDAO = new FeedbackDAO();
        try {
            feedbackDAO.addFeedback(feedback);
            request.setAttribute("message", "Feedback submitted successfully!");
            request.setAttribute("alertClass", "alert-success");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while submitting feedback.");
            request.setAttribute("alertClass", "alert-danger");
        }

        request.getRequestDispatcher("/doctors/doctorHome.jsp").forward(request, response);
    }
}

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.groupd.beans.User" %>

<%
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    String role = user.getRole();
    String requestURI = request.getRequestURI();
    String contextPath = request.getContextPath();

    // Define role-specific home pages
    String patientHome = contextPath + "/patients/patientHome.jsp";
    String doctorHome = contextPath + "/doctors/doctorHome.jsp";
    String staffHome = contextPath + "/staffs/staffHome.jsp";

//    // Redirect based on role and avoid redirection loop
//    if ("patient".equals(role) && !requestURI.endsWith("patientHome.jsp")) {
//        response.sendRedirect(patientHome);
//        return;
//    }
//
//    if ("doctor".equals(role) && !requestURI.endsWith("doctorHome.jsp")) {
//        response.sendRedirect(doctorHome);
//        return;
//    }
//
//    if ("staff".equals(role) && !requestURI.endsWith("staffHome.jsp")) {
//        response.sendRedirect(staffHome);
//        return;
//    }
%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">HMS | <%= user.getFirstName() %></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <% if ("doctor".equals(role)) { %>
            <li class="nav-item">
                <a class="nav-link <%= (request.getRequestURI().endsWith("/doctorHome.jsp") ? "active" : "") %>" href="<%= doctorHome %>">Appointments</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%= (request.getRequestURI().endsWith("/doctorProfile.jsp") ? "active" : "") %>" href="<%= contextPath %>/doctors/doctorProfile.jsp">Manage Profile</a>
            </li>
            <% } else if ("patient".equals(role)) { %>
            <li class="nav-item">
                <a class="nav-link <%= (request.getRequestURI().endsWith("/patientHome.jsp") ? "active" : "") %>" href="<%= patientHome %>">View Appointments</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%= (request.getRequestURI().endsWith("/patientMedicalHistory.jsp") ? "active" : "") %>" href="<%= contextPath %>/patients/patientMedicalHistory.jsp">Medical History</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%= (request.getRequestURI().endsWith("/patientProfile.jsp") ? "active" : "") %>" href="<%= contextPath %>/patients/patientProfile.jsp">Patient Profile</a>
            </li>
            <% } else if ("staff".equals(role)) { %>
            <li class="nav-item">
                <a class="nav-link <%= (request.getRequestURI().endsWith("/staffHome.jsp") ? "active" : "") %>" href="<%= staffHome %>">Book Appointment</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%= (request.getRequestURI().endsWith("/staffManageBilling.jsp") ? "active" : "") %>" href="<%= contextPath %>/staffs/staffManageBilling.jsp">Create Billing</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%= (request.getRequestURI().endsWith("/staffViewEditPatient.jsp") ? "active" : "") %>" href="<%= contextPath %>/staffs/staffViewEditPatient.jsp">Edit Patient</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%= (request.getRequestURI().endsWith("/staffRegisterPatient.jsp") ? "active" : "") %>" href="<%= contextPath %>/staffs/staffRegisterPatient.jsp">New Patients</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%= (request.getRequestURI().endsWith("/staffRegisterDoctor.jsp") ? "active" : "") %>" href="<%= contextPath %>/staffs/staffRegisterDoctor.jsp">New Doctors</a>
            </li>
            <% } %>
            <li class="nav-item">
                <a class="nav-link" href="<%= contextPath %>/logout">Logout</a>
            </li>
        </ul>
    </div>
</nav>

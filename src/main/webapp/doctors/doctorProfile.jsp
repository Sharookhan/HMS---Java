<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.groupd.beans.Doctor" %>

<%
    Doctor doctor = (Doctor) request.getAttribute("doctor");

    if (doctor == null) {
%>
<h2>Doctor Profile</h2>
<p>No profile information available.</p>
<%
} else {
%>
<h2>Doctor Profile</h2>
<p><strong>ID:</strong> <%= doctor.getDoctorId() %></p>
<p><strong>First Name:</strong> <%= doctor.getFirstName() %></p>
<p><strong>Last Name:</strong> <%= doctor.getLastName() %></p>
<p><strong>Specialization:</strong> <%= doctor.getSpecialization() %></p>
<p><strong>Phone Number:</strong> <%= doctor.getPhoneNumber() %></p>
<p><strong>Email:</strong> <%= doctor.getEmail() %></p>
<p><strong>Profile:</strong> <%= doctor.getProfile() %></p>
<%
    }
%>

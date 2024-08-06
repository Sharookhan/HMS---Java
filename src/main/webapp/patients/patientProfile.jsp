<%--
  Created by IntelliJ IDEA.
  User: JamesMcDuck
  Date: 2024-08-04
  Time: 11:38 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.groupd.beans.Patient" %>
<%@ page import="com.groupd.dao.PatientDAO" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HMS - Patient Home</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/master.css" rel="stylesheet">
</head>
<body>
<%@ include file="../common/navbar.jsp" %>

<%
    PatientDAO patientDAO = new PatientDAO();

    Patient patient = patientDAO.getPatient(user.getId());

%>

    <div class="container mt-4">
        <h1>Patient's Profile</h1>

        <div class="container">
            <table class="table">
                <tr>
                    <th>First Name</th>
                    <td><%= patient.getFirstName() %></td>
                </tr>
                <tr>
                    <th>Last Name</th>
                    <td><%= patient.getLastName() %></td>
                </tr>
                <tr>
                    <th>DoB</th>
                    <td><%= patient.getDateOfBirth() %></td>
                </tr>
                <tr>
                    <th>Gender</th>
                    <td><%= patient.getGender() %></td>
                </tr>
                <tr>
                    <th>Phone Number</th>
                    <td><%= patient.getPhoneNumber() %></td>
                </tr>
                <tr>
                    <th>Email</th>
                    <td><%= patient.getEmail() %></td>
                </tr>
                <tr>
                    <th>Address</th>
                    <td><%= patient.getAddress() %></td>
                </tr>
            </table>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

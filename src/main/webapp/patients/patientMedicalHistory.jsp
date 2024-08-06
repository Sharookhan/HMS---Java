<%--
  Created by IntelliJ IDEA.
  User: JamesMcDuck
  Date: 2024-07-25
  Time: 6:39 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.groupd.beans.MedicalHistory" %>
<%@ page import="com.groupd.dao.MedicalHistoryDAO" %>

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
    MedicalHistoryDAO medicalHistoryDAO = new MedicalHistoryDAO();

    MedicalHistory mh = medicalHistoryDAO.getPatientMedicalHistory(user.getId());

%>

    <div class="container mt-4">
        <h1>Medical History of <%= user.getFirstName() %> <%= user.getLastName() %> </h1>

        <div class="container">
            <table class="table">
                <tr>
                    <th>Diagnosis</th>
                    <th>Treatment</th>
                    <th>Notes</th>
                </tr>
                <tr>
                    <td><%= mh.getDiagnosis() %></td>
                    <td><%= mh.getTreatment() %></td>
                    <td><%= mh.getNotes() %></td>
                </tr>
            </table>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

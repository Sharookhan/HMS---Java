<%@ page import="com.groupd.beans.Bill" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Billing</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/master.css" rel="stylesheet">
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<div class="container mt-4 form-container">
    <!-- Display error/success messages -->
    <%
        String message = (String) request.getAttribute("message");
        String alertClass = (String) request.getAttribute("alertClass");
        if (message != null && alertClass != null) {
    %>
    <div class="alert <%= alertClass %>" role="alert">
        <%= message %>
    </div>
    <%
        }
    %>

    <!-- Billing Form -->
    <h2>Manage Billing</h2>
    <form action="${pageContext.request.contextPath}/addBill" method="post" class="mb-4">
        <div class="form-group">
            <label for="patientId">Patient ID:</label>
            <input type="text" id="patientId" name="patientId" class="form-control" value="<%= request.getAttribute("selectedPatientId") != null ? request.getAttribute("selectedPatientId") : "" %>" required>
        </div>
        <div class="form-group">
            <label for="amount">Amount:</label>
            <input type="number" id="amount" name="amount" class="form-control" step="0.01" min="0" required>
        </div>
        <div class="form-group">
            <label for="billDate">Bill Date:</label>
            <input type="date" id="billDate" name="billDate" class="form-control" value="<%= new java.sql.Date(System.currentTimeMillis()).toString() %>" required>
        </div>
        <div class="form-group">
            <label for="status">Status:</label>
            <select id="status" name="status" class="form-control" required>
                <option value="Unpaid">Unpaid</option>
                <option value="Paid">Paid</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Add Bill</button>
    </form>

    <hr>

    <!-- Pending Payments Section -->
    <h2>Pending Payments</h2>
    <form action="${pageContext.request.contextPath}/addBill" method="get" class="mb-4">
        <div class="form-group">
            <label for="patientIdSelect">Select Patient ID:</label>
            <input type="text" id="patientIdSelect" name="patientId" class="form-control" value="<%= request.getAttribute("selectedPatientId") != null ? request.getAttribute("selectedPatientId") : "" %>">
        </div>
        <button type="submit" class="btn btn-primary">Show Pending Payments</button>
    </form>

    <!-- Display pending bills or message if none -->
    <%
        List<Bill> pendingBills = (List<Bill>) request.getAttribute("pendingBills");
        boolean isFetching = request.getParameter("patientId") != null && !request.getParameter("patientId").isEmpty();
        if (pendingBills != null && !pendingBills.isEmpty()) {
    %>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Bill ID</th>
            <th>Patient ID</th>
            <th>Staff ID</th>
            <th>Amount</th>
            <th>Bill Date</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Bill bill : pendingBills) {
        %>
        <tr>
            <td><%= bill.getBillId() %></td>
            <td><%= bill.getPatientId() %></td>
            <td><%= bill.getStaffId() %></td>
            <td><%= bill.getAmount() %></td>
            <td><%= bill.getBillDate() %></td>
            <td><%= bill.getStatus() %></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
    } else if (isFetching) {
    %>
    <div class="alert alert-info" role="alert">
        No pending bills found for the selected patient.
    </div>
    <%
        }
    %>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

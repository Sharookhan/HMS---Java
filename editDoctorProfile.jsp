<%@ page import="com.groupd.beans.Doctor" %>
<%@ page import="java.sql.Date" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Doctor Profile</title>
</head>
<body>
    <h1>Edit Profile</h1>

    <form action="<%= request.getContextPath() %>/editDoctorProfile" method="post">
        <input type="hidden" name="doctorId" value="<%= request.getAttribute("doctor").getDoctorId() %>" />
        <label>First Name:</label>
        <input type="text" name="firstName" value="<%= request.getAttribute("doctor").getFirstName() %>" /><br />
        <label>Last Name:</label>
        <input type="text" name="lastName" value="<%= request.getAttribute("doctor").getLastName() %>" /><br />
        <label>Specialty:</label>
        <input type="text" name="specialty" value="<%= request.getAttribute("doctor").getSpecialty() %>" /><br />
        <label>Email:</label>
        <input type="text" name="email" value="<%= request.getAttribute("doctor").getEmail() %>" /><br />
        <input type="submit" value="Update Profile" />
    </form>
</body>
</html>

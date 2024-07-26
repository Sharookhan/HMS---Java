<!DOCTYPE html>
<html>
<head>
    <title>Doctor Home</title>
</head>
<body>
    <!-- Existing content -->

    <h2>Submit Feedback</h2>
    <form action="<%= request.getContextPath() %>/submitFeedback" method="post">
        <input type="hidden" name="doctorId" value="<%= user.getId() %>" />
        <textarea name="feedback" rows="4" cols="50"></textarea><br />
        <input type="submit" value="Submit Feedback" />
    </form>
</body>
</html>

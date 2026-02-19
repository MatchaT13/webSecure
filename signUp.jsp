<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BrightCare | Register</title>
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
   <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark" style="background-color:#00897B;">
    <div class="container">
        <a class="navbar-brand fw-bold">BrightCare</a>
    </div>
</nav>

<div class="signup-wrapper">
    <div class="signup-container">
        <h2>Create an account</h2>

        <!-- Display error message -->
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <div class="alert alert-danger"><%= error %></div>
        <%
            }
        %>

        <form action="signUpServlet" method="post">

            <label>Register As</label>
            <select class="form-select" name="role">
                <option value="patient">Patient</option>
                <option value="dentist">Dentist</option>
                <option value="staff">Clinic Staff</option>
            </select><br>

            <label>Full Name</label>
            <input type="text" name="fullname" class="form-control" required>

            <label>Email</label>
            <input type="email" name="email" class="form-control" required>

            <label>Date of Birth</label>
            <input type="date" name="dob" class="form-control" required>

            <label>Password</label>
            <input type="password" name="password" class="form-control" required>

            <label>Confirm Password</label>
            <input type="password" name="confirmPassword" class="form-control" required>

            <button type="submit" class="btn btn-success mt-3 w-100">Register</button>
        </form>

        <p class="mt-3">Already a member? <a href="login.jsp">Sign in</a></p>
    </div>
</div>

</body>
</html>

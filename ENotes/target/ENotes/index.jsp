<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home page</title>
    <%@ include file="all_component/allcss.jsp" %> <!-- Ensure this path is correct -->
    <style type="text/css">
        .back-img {
            background: url("img/notes.jpg");
            background-size: cover;
            width: 100%;
            height: 90vh;
        }

        /* Adjusting the footer to always stick at the bottom */
        html, body {
            height: 100%;
            margin: 0;
        }

        .container-fluid {
            min-height: 90vh; /* Ensures that content area takes the full height minus footer space */
        }

        .footer {
            background-color: #333;
            color: white;
            text-align: center;
            padding: 20px;
            position: relative;
            bottom: 0;
            width: 100%;
        }
    </style>
</head>
<body>
    <%@ include file="all_component/navbar.jsp" %> <!-- Ensure this path is correct -->

    <!-- Main Content Section -->
    <div class="container-fluid back-img">
        <div class="text-white text-center">
            <h1><i class="fa-solid fa-book" style="padding: 50px 0px 20px"></i> E-Notes Save your notes</h1>
            <a href="login.jsp" class="btn btn-light" style="margin-right:10px">
                <i class="fa-solid fa-right-to-bracket"></i> Login
            </a>
            <a href="register.jsp" class="btn btn-light">
                <i class="fa fa-user-plus"></i> Register
            </a>
        </div>
    </div>

    <!-- Footer Section -->
    <div class="footer">
        <p>&copy; 2025 E-Notes. All rights reserved.</p>
    </div>

    <%@ include file="all_component/footer.jsp" %> <!-- Ensure this path is correct -->
</body>
</html>

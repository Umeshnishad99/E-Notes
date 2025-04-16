<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <%@ include file="all_component/allcss.jsp" %>
</head>
<body>

    <% session.removeAttribute("user"); %>
    <%@ include file="all_component/navbar.jsp" %>

    <div class="container-fluid div-color d-flex justify-content-center align-items-center" style="min-height: 100vh;">
        <div class="col-md-5 col-lg-4">
            <div class="card shadow-lg rounded-4">
                <div class="card-header text-center text-white bg-custom rounded-top-4 py-4">
                    <i class="fa-solid fa-right-to-bracket fa-3x mb-2"></i>
                    <h4 class="mb-0">Login</h4>
                </div>

                <div class="card-body p-4">

                    <!-- Alert Messages -->
                    <c:choose>
                        <c:when test="${not empty sessionScope['loginfail']}">
                            <div class="alert alert-danger" role="alert">
                                ${sessionScope['loginfail']}
                            </div>
                            <%
                                session.removeAttribute("loginfail");
                            %>
                        </c:when>
                        <c:when test="${not empty sessionScope['loginMustMsg']}">
                            <div class="alert alert-warning" role="alert">
                                ${sessionScope['loginMustMsg']}
                            </div>
                            <%
                                session.removeAttribute("loginMustMsg");
                            %>
                        </c:when>
                    </c:choose>

                    <!-- Login Form -->
                    <form method="post" action="logUser" novalidate>
                        <div class="mb-3">
                            <label for="userEmail" class="form-label">Email address</label>
                            <input type="email" class="form-control" id="userEmail" name="userEmail" required placeholder="Enter your email">
                        </div>
                        <div class="mb-4">
                            <label for="userPassword" class="form-label">Password</label>
                            <input type="password" class="form-control" id="userPassword" name="userPassword" required placeholder="Enter your password">
                        </div>
                        <button type="submit" class="btn btn-primary w-100 rounded-pill">Login</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="all_component/footer.jsp" %>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Home Page</title>
    <%@ include file="all_component/allcss.jsp" %>
</head>
<body>

    <!-- Redirect to login if user is not in session -->
    <c:choose>
        <c:when test="${empty sessionScope['user']}">
            <c:set var="loginMustMsg" value="Please Login first." scope="session" />
            <c:redirect url="login.jsp" />
        </c:when>
    </c:choose>

    <!-- Main content -->
    <div class="container-fluid p-0">
        <%@ include file="all_component/navbar.jsp" %>

        <h1 class="text-center my-3"> Add Your Note </h1>

        <div class="container">

            <!-- Show success message -->
            <c:if test="${not empty sessionScope['added']}">
             <div class="alert alert-success alert-dismissible fade show" role="alert">
                 <strong>Success!</strong> ${sessionScope['added']}
                 <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                     <span aria-hidden="true">&times;</span>
                 </button>
             </div>
             <c:remove var="added" scope="session" />
         </c:if>
                
            <!-- Note form -->
            <div class="row">
                <div class="col-md-12">
                    <form method="post" action="addNote">
                        <div class="form-group">
                            <label class="h5" for="noteTitle">Note Title</label>
                            <input type="text" class="form-control" id="noteTitle" placeholder="Enter your title here..." name="noteTitle" required="required">
                        </div>
                        <div class="form-group">
                            <label class="h5" for="noteContent">Note Content</label>
                            <textarea rows="11" class="form-control" placeholder="Enter your content here..." id="noteContent" name="noteContent" required="required" style="resize: none;"></textarea>
                        </div>
                        <div class="container text-center mt-3 mb-5">
                            <button type="submit" class="btn btn-primary">Add Note</button>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    </div>

    <%@ include file="all_component/footer.jsp" %>

</body>
</html>

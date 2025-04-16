<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Note</title>
    <%@ include file="all_component/allcss.jsp" %>
    <!-- Include Bootstrap CSS for better UI -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Include jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="container-fluid p-0">
        <%@ include file="all_component/navbar.jsp" %>

        <h1 class="text-center my-3">Edit Your Note</h1>

        <div class="container">
            <!-- Show success or error message -->
            <c:if test="${not empty sessionScope.edited}">
                <div class="alert alert-success" role="alert">${sessionScope.edited}</div>
                <%
                    session.removeAttribute("edited");
                %>
            </c:if>

            <!-- Note Edit Form -->
            <div class="row">
                <div class="col-md-12">
                    <form id="editNoteForm">
                        <input type="hidden" name="noteId" id="noteId" value="${note.id}">

                        <!-- Note Title -->
                        <div class="form-group">
                            <label class="h5" for="noteTitle">Note Title</label>
                            <input type="text" class="form-control" id="noteTitle" name="noteTitle" 
                                   value="${note.title}" required>
                        </div>

                        <!-- Note Content -->
                        <div class="form-group">
                            <label class="h5" for="noteContent">Note Content</label>
                            <textarea class="form-control" id="noteContent" name="noteContent" 
                                      rows="11" required style="resize: none;">${note.content}</textarea>
                        </div>

                        <!-- Submit Button -->
                        <div class="container text-center mt-3 mb-5">
                            <button type="submit" class="btn btn-primary">Update Note</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <%@ include file="all_component/footer.jsp" %>

        <!-- AJAX to handle form submission without page reload -->
        <script>
            $(document).ready(function() {
                $('#editNoteForm').submit(function(e) {
                    e.preventDefault(); // Prevent the default form submission

                    var noteId = $('#noteId').val();
                    var noteTitle = $('#noteTitle').val();
                    var noteContent = $('#noteContent').val();

                    // Disable the submit button to prevent multiple submissions
                    $('button[type="submit"]').prop('disabled', true);

                    $.ajax({
                        url: 'editNotes', // Servlet URL
                        method: 'POST',
                        data: {
                            noteId: noteId,
                            noteTitle: noteTitle,
                            noteContent: noteContent
                        },
                        success: function(response) {
                            var res = JSON.parse(response); // Parse JSON response

                            if (res.status === "success") {
                                // If the update was successful, update the page content and show success message
                                $('#noteTitle').val(res.noteTitle); // Update note title
                                $('#noteContent').val(res.noteContent); // Update note content

                                alert("Note updated successfully!");
                                window.location.href = "showNotes"; // Optionally, redirect to the notes list page
                            } else {
                                // Show failure message
                                alert("Error: " + res.message);
                            }
                        },
                        error: function(xhr, status, error) {
                            alert("An error occurred: " + error);
                        },
                        complete: function() {
                            // Re-enable the submit button
                            $('button[type="submit"]').prop('disabled', false);
                        }
                    });
                });
            });
        </script>
    </div>
</body>
</html>

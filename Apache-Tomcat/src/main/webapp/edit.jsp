<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="bootstrap-4/css/bootstrap.min.css" type="text/css">
    <!-- Optional JavaScript -->
    <script src="bootstrap-4/js/jquery-3.3.1.min.js"></script>
    <script src="bootstrap-4/js/bootstrap.min.js"></script>

    <title>Edit Post</title>
</head>
<body>

<h1 style="color:#333;text-align:center;">Edit Post</h1>
<form action="post" method="POST">
    <nav class="navbar navbar-light bg-light justify-content-center">
        <div style="text-align:center">
            <button type="submit" class="btn btn-outline-success" name="action" value="save">Save</button>
            <button type="submit" class="btn btn-outline-dark" name="action" value="list">Close</button>
            <button type="submit" class="btn btn-outline-info" name="action" value="preview">Preview</button>
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#exampleModal">Delete</button>

            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Warning</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            Are you sure to delete this post?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary" name="action" value="delete" class="delete">Delete</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>
    <input type="hidden" name="username" value="<%= request.getParameter("username") %>">
    <input type="hidden" name="postid" value="<%= request.getParameter("postid") %>">

    <div class="col-auto my-1">
        <label for="title">Title</label><br>
        <input type="text" class="form-control" id="title" name="title" value="<%= request.getAttribute("title") %>">
    </div>
    <div class="col-auto my-1">
        <label for="body">Body</label><br>
        <textarea class="form-control" id="body" name="body" rows="15"><%= request.getAttribute("body") %></textarea>
    </div>
</form>

</body>
</html>
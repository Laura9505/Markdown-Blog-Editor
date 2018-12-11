<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!DOCTYPE html>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="bootstrap-4/css/bootstrap.min.css" type="text/css">
        <!-- Optional JavaScript -->
        <script src="bootstrap-4/js/jquery-3.3.1.min.js"></script>
        <script src="bootstrap-4/js/bootstrap.min.js"></script>

        <title>Preview Post</title>
    </head>
    <body>
        <form action="post" method="POST">
        	<button type="submit" class="btn btn-secondary" name="action" value="open">Close Preview</button>
            <input type="hidden" name="username" value="<%= request.getParameter("username") %>">
            <input type="hidden" name="postid" value="<%= request.getParameter("postid") %>">
            <input type="hidden" name="title" value="<%= request.getParameter("title") %>">
            <input type="hidden" name="body" value="<%= request.getParameter("body") %>">
        </form>
        <br><br>
        <div class="jumbotron">
            <div class="card-body">
                <%= request.getAttribute("markdown") %>
            </div>
        </div>
    </body>
</html>

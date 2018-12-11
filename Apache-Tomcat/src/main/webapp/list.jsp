<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!DOCTYPE html>
<%@ page import="java.util.*" %>
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

        <title>Post List</title>
    </head>
    <body>
        <form action="post" method="POST">
        	<button type="submit" class="btn btn-primary" name="action" value="open">New Post</button>
            <input type="hidden" name="username" value=<%= request.getParameter("username") %> >
            <input type="hidden" name="postid" value="0">
        </form>
        <% List<List<String>> table = (List<List<String>>)request.getAttribute("table"); %>
        <br>
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">Title</th>
                <th scope="col">Created</th>
                <th scope="col">Modified</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <% for (int i=0; i<table.size(); i++){ 
                List<String> row = table.get(i); %>
            <tr>
                <td> <%= row.get(1) %> </td>
                <td> <%= row.get(2) %> </td>
                <td> <%= row.get(3) %> </td>
                <td>
                    <form action="post" method="POST">
                        <button type="submit" class="btn btn-warning" name="action" value="open" class="open">Open</button>
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#exampleModal">Delete</button>

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
                        <input type="hidden" name="username" value=<%= request.getParameter("username") %>>
                        <input type="hidden" name="postid" value=<%= row.get(0) %>>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
    </body>
</html>

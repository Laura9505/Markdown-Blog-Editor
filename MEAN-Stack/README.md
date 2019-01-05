# Markdown-Blog-Editor-MEAN-Stack
A markdown Blog Editor Website based on MEAN stack that 
(1) lets anyone read blogs written by our users through public URLs and 
(2) lets our registered users create and update their own blogs after password authentication. 

The back-end service that implement the RESTful API to save/delete and post blogs is developed using MongoDB, NodeJS and Express application generator. JSON Web Token (JWT) is used to authenticate users. The front-end blog editor and previewer use Angular, a popular front-end Web-development framework, to develop a more advanced and dynamic version of markdown blog editor 


## Front End
---
Developed by Angular CLI (v7.0.3).

URL | state
------------ | ------------- 
/editor/#/ | This default path shows only the list pane, without showing the edit or preview view
/editor/#/edit/:id | This path shows the list pane and the “edit view” for the post with postid=id
/editor/#/preview/:id	| This path shows the list pane and the “preview view” of the post with postid=id

edit view
---

preview view
---

list pane
---



## Back End
---
Developed by MongoDB (v3.6.8), NodeJS (v8.12.0) and Express application generator (v4.16.0).

The back-end blogging service is accessible at the following URLs:

\#  | URL | method | functionality
-- | ----- | ----- | ------------- 
1  | /blog/:username/:postid | GET | Return an HTML-formatted page that shows the blog post with postid written by username.
2  | /blog/:username | GET  | Return an HTML page that contains first 5 blog posts by username.
3  | /login | GET, POST | Authenticate the user through username and password.
4  | /api/:username | GET | This is the REST API used to retrieve all blog posts by username.
5  | /api/:username/:postid | GET, POST, PUT, DELETE | This is the REST API used to perform a CRUD operation on the user’s blog post	

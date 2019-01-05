 # Markdown-Blog-Editor-Apache-Tomcat
 implementation of an online markdown editor using MySQL and Tomcat.


Edit Page
---
The “edit page” allows editing the title and body of a post.
![name](./edit.png 'edit')

The page contains four buttons: save, close, preview, and delete. Once pressed,
- “save” button saves the content of the post to the database and goes to the “list page”.
- “close” button goes to the “list page” without saving the current content.
- “preview” button goes to the “preview page” (without saving the current content).
- “delete” button deletes the post from the database and goes to the “list page”.


Preview page
---
The “preview page” shows the HTML rendering of a post written in markdown. The page has a “close” button. Once pressed, close button goes back to the “edit page” of the post.
![name](./preview.png 'preview')

List page
---
![name](./list.png 'list')

The “list page” shows the list of all blog posts saved by the user. The posts in the list are sorted by their “postid” (a unique integer assigned to a post) in the ascending order. Each item in the list shows:
1. title, creation, and modification dates of the post, and
2. two buttons: open and delete. Once pressed,
    - “open” button goes to the “edit page” for the post.
    - “delete” button deletes the post from the database and comes back to the list page.

The list page also contains a “new post” button to allow users to create a new post. Once pressed, the button should lead to the “edit page” for a new post.



develop a Web application using Java Servlet and JSP, and how you can package the set of files needed for your app into a single Web Application Archive (WAR) file for easy deployment on Tomcat.

allows users to create a new post (written in markdown), preview the post (rendered in HTML), and manage existing posts

use JDBC (Java DataBase Connectivity) API to access MySQL data

allows users to save and edit blog posts written in markdown.

we will use an “off-the-shelf” markdown parsing library in Java [markdown parsing library](https://github.com/atlassian/commonmark-java, "markdown parsing library")

used Bootstrap4 library to 

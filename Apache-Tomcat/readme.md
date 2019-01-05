 # Markdown-Blog-Editor-Apache-Tomcat
implementation of an online markdown editor using MySQL and Tomcat.

- allows users to create a new post (written in markdown), preview the post (rendered in HTML), and manage existing posts
- develops this Web application using Java Servlet and JSP (Java ServerPages), and using Web Application Archive (WAR) file for easy deployment on Tomcat.
- uses JDBC (Java DataBase Connectivity) API to access MySQL data
- to implement the “preview page”, you need to “compile” a markdown-formatted input into an HTML-formatted output. For this,  the [commonmark Java library] (https://github.com/atlassian/commonmark-java, "markdown parsing library") is used here.
- uses Bootstrap-4 Web front-end library for styling the website.


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


Server-Side API
---
This web application follows the following REST API:
'''
/editor/post?action=type&username=name&postid=num&title=title&body=body
'''
The path is case sensitive. The parameter “action” specifies one of five “actions”: open, save, delete, preview, and list. The other four parameters, username, postid, title, and body are (optional) parameters that the actions may need.

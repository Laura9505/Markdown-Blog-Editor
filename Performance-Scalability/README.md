# Testing Performance of Server(s)
Testing the performance of the server (measuring the number of concurrent requests that a system can handle) and find the maximum number of users that the server could handle under the performance requirements that the servers must return at least 98% responses within less than 400ms for all requests except login requests., using Locust (v0.9.0).

Mock data preparation: Before test, the databases have been populated with 500 fake blog posts by the user cs144.


Locust File for Tomcat Server
---
- read_tomcat.py

simulating the scenario where all requests from users are read intensive. The user whose name is cs144 would randomly open one of his posts via /editor/post?action=open&username=cs144&postid={num}, where {num} is a random postid.

- write_tomcat.py

simulating the scenario where the requests from users are write intensive. The user cs144 would modify one of his posts randomly by changing the title and the body.

- mixed_tomcat.py

simulating a more realistic scenario where some users are reading posts while others are writing. In this test, 10% of users are write intensive while the remaining 90% are read intensive.


Locust File for Node Server
---
- read_node.py

simulating a similar behavior of read_tomcat.py except that now we are testing the performance of our Node.JS server

- write_node.py

testing the server performance under write intensive requests as we did in write_tomcat.py. The user cs144 randomly update one of her posts by changing its title and body.

- mixed_node.py

combining the read intensive tasks and write intensive ones in a single file as you would expect. The percentage would remain the same as 10% write and 90% read.

Results
---
1. The results after running read_tomcat.py, write_tomcat.py, read_node.py, and write_node.py are in the performance.json file.
2. The results of the maximum number of users that the server could handle under 10%-write and 90%-read load using mixed_tomcat.py and mixed_node.py files are recorded in summary_tomcat.txt and summary_node.txt.

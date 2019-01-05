import sys, random
from locust import HttpLocust, TaskSet

def readPost(locust):
	""" define a function in python whose name is previewPage and the argument is locust """
	postid = random.randint(1, 500) # generate a random number from 1 to 100 (include 1 and 100)
	url_prefix = '/editor/post'
	url_params = '?action=open&username=cs144&postid='
	locust.client.get(url_prefix + url_params + str(postid), name=url_prefix)

def writePost(locust):
	""" define a function in python whose name is previewPage and the argument is locust """
	postid = random.randint(1, 500) # generate a random number from 1 to 100 (include 1 and 100)
	url_prefix = '/editor/post'
	url_params = '?action=save&username=cs144&postid={num}&title=Loading%20Test&body=***Hello%20World!***'
	locust.client.post(url_prefix + url_params.format(num=postid), name=url_prefix)

class MyTaskSet(TaskSet):
	""" the class MyTaskSet inherits from the class TaskSet, defining the behavior of the user """
	tasks = {readPost: 9, writePost: 1}

class MyLocust(HttpLocust):
	""" the class MyLocust inherits from the class HttpLocust, representing an HTTP user """
	task_set = MyTaskSet
	min_wait = 1000
	max_wait = 2000

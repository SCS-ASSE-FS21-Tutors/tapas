# tapas-tasks

Micro-service for Managing Tasks in a Task List implemented following Hexagonal Architecture.

Based on examples from book "Get Your Hands Dirty on Clean Architecture" by Tom Hombergs

Technologies: Java, Spring Boot, Maven

**Note:** this repository contains an [EditorConfig](https://editorconfig.org/) file (`.editorconfig`)
with default editor settings. EditorConfig is supported out-of-the-box by the IntelliJ IDE. To help maintain
consistent code styles, we recommend to reuse this editor configuration file in all your services.

## HTTP API Overview
The code we provide includes a minimalistic HTTP API for (i) creating a new task and (ii) retrieving
the representation of a task.

For further developing and working with your HTTP API, we recommend to use [Postman](https://www.postman.com/).

### Creating a new task

A new task is created via an `HTTP POST` request to the `/tasks/` endpoint. The body of the request
must include a JSON payload with the content type `application/json` and two required fields:
* `taskName`: a string that represents the name of the task to be created
* `taskType`: a string that represents the type of the task to be created

A sample HTTP request with `curl`:
```shell
curl -i --location --request POST 'http://localhost:8081/tasks/' --header 'Content-Type: application/json' --data-raw '{
    "taskName" : "task1",
    "taskType" : "type1"
}'

HTTP/1.1 201
Content-Type: application/json
Content-Length: 142
Date: Sun, 03 Oct 2021 17:25:32 GMT

{
  "taskType" : "type1",
  "taskState" : "OPEN",
  "taskListName" : "tapas-tasks-tutors",
  "taskName" : "task1",
  "taskId" : "53cb19d6-2d9b-486f-98c7-c96c93b037f0"
}
```

If the task is created successfuly, a `201 Created` status code is returned together with a JSON
representation of the created task. The representation includes, among others, a _universally unique
identifier (UUID)_ for the newly created task (`taskId`).

### Retrieving a task

The representation of a task is retrieved via an `HTTP GET` request to the `/tasks/<task-UUID>` endpoint.

A sample HTTP request with `curl`:
```shell
curl -i --location --request GET 'http://localhost:8081/tasks/53cb19d6-2d9b-486f-98c7-c96c93b037f0'

HTTP/1.1 200
Content-Type: application/json
Content-Length: 142
Date: Sun, 03 Oct 2021 17:27:06 GMT

{
  "taskType" : "type1",
  "taskState" : "OPEN",
  "taskListName" : "tapas-tasks-tutors",
  "taskName" : "task1",
  "taskId" : "53cb19d6-2d9b-486f-98c7-c96c93b037f0"
}
```

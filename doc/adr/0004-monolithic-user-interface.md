# 4. Monolithic User Interface

Date: 2021-09-27

## Status

Accepted

## Context

For users to add a new task to be executed to the task list and to remove tasks, the application needs a simple user interface. Furthermore, the user interface shall display status information of tasks and offer the possibility to search and filter tasks in the task list. 

## Decision

We will create a single user interface after the monolithic user interface pattern for microservices architectures. The frontend calls the user requests through the API layer. The user interface should offer the user options to add tasks to the task list, view the current status and history of finished tasks, and offer some filtering options for the task list.

## Consequences

Changes to the task list setup would require testing and deployment of the user interface functions as well.

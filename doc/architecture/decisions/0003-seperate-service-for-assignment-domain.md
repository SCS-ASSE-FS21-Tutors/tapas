# 3. Seperate service for assignment domain

Date: 2021-10-18

## Status

Accepted

## Context

The Assignment Service handles the assignment of a task to a corresponding and available executor. It keeps track of all the connections between tasks and executors.

## Decision

The assignment domain will be its own service.
The assignment service will be a central point in our application. It will have most of the business logic in it and will communicate with all the different services. Therefore, other services can be kind of “dumb” and only need to focus on their simple tasks.
The code of the assignment will change more often than the code of the other services, thus having the assignment service split from the other makes it more deployable.

## Consequences

Having this system as its own service we reduce the Fault tolerance because the assignment service can be the single point of failure. We can mitigate this risk by implementing (server) replication and/or having an event driven communication with persisting messages. Therefore, all other services can run independently, and the assignment service can recover from a crash.

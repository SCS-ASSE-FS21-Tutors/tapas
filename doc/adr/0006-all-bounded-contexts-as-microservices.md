# 6. All bounded contexts as microservices

Date: 2021-10-07

## Status

Accepted

## Context

The bounded context have been chosen in a way that they are easily separable. Additionally, the *Task List* service is already given.

## Decision

A microservice is created for every bounded context.

Next to the existing microservices, two new ones are created: *Executor Pool* and *Roster*. This resolves the event-based *Roster Management* block and brings the architecture to a pure microservice architecture.

Both of the two new microservices feature an own database, facilitating more asynchronous communication and independence.

## Consequences

Separating the services eliminates the single point of failure *Roster Management* and separates functionality at the same level as the existing services. As the *Roster Management* block would have been event-driven, we neither expect higher latency nor additional complexity nor less fault tolerance.

Instead, complexity is reduced as no event channels and database access coordination is necessary.
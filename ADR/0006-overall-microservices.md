# 6. Overall Microservices

Date: 2021-10-12

## Status

Accepted

## Context

We need a fundamental structure of the whole system. 
Based on the identified bounded contexts and their respective nonfunctional properties.
## Decision

Based on the idea of event storming, each bounded context will be represented by an individual component of the system.
The overall architecture is based on microservices. This decision is based on the identified driving characteristics.
Microservices allow for scalability, easy and independent deployment of the identified individual components.
Since we need to support a continuous workflow, we can make use of the architecture's fault tolerance.

Only the roster and the TaskList are implemented service based as mentioned in 
[ADR 003](0003-roster-task-service-based.md).

## Consequences

Since every component in a microservice based architecture still features its own architectural
style based on the specific requirements, we will need to select an architecture for each component.
Since microservices feature a separate database for each individual component,
we need to take care of the exchange of information between services, which adds complexity.
The databases also need to be recovered upon failure to ensure the system to work properly.


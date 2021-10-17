# 6. One global database or one database per service

Date: 2021-10-18

## Status

Accepted

## Context

We can have one database for all services or each Microservice can have its own database.

## Decision

Each Microservice will have its own database.
The different services don’t need to store a lot of similar data. Therefore, we can have every Microservice handle its own data. This also gives the advantage that every Microservice owns its own data and is also responsible for it. (Data ownership, Data responsibility).

## Consequences

Having one database per Microservice will lead to eventual consistency. Having an event driven communication we can use event-based synchronisation to keep the data in sync between the services, thus the individual services don’t need to know about each other. To guarantee data consistency we can also use a pattern like sagas.

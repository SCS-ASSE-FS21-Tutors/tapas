# 7. Executors as services

Date: 11.10.2021

## Status

Accepted

## Context

Executors are lighter than anticipated. Therefore, we can expect that they need no separate data
storage and contain little business logic.

## Decision

Instead of using a Microservice for every Executors, we create the single _Executors_ Microservice which
acts as hub for all Executors.

## Consequences

`tapas-executor-digital` and `tapas-executor-robot` are replaced by `tapas-executors`. No individual data
storage for each Microservice is necessary. Therefor, a way to run multiple executors simultaneously has to be found.
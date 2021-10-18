# 2. Seperate service for Executors

Date: 2021-10-18

## Status

Accepted

## Context

The users need to be able to add new executors to the executor pool. The functionality of the executor is currently unknown.

## Decision

We will use a separate microservice for each executor.
New executors will be added/removed during runtime. Therefore, we need a high extensibility.
Different executors can have different execution times and a different load. This means the executors scale differently.

## Consequences

Having executors as its own service we can deploy new executors independently and easily add new executors during runtime and guarantee high scalability as well as evolvability.

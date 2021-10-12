# 2. Executor Microservices

Date: 2021-09-27

## Status

Accepted

## Context

We need a useful separation for the different exuctor functionalities to work independently and be able to evolve the component without redeploying it.
## Decision

Executors will be implemented as microservices.

## Consequences

Each executor has its own data environment. Consequently, it will be difficult for executors to work on a task together. However, since this is not required, it is not an issue.

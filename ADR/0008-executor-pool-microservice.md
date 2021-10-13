# 8. Adaptable Executor Pool

Date: 2021-10-12

## Status

Accepted

Supersedes [4. Executor Pool Microkernel Microservices Architecture](0004-executor-pool-microkernel-microservices-architecture.md)


## Context
We need to be able to add Executors to the ExecutorPool, as well as remove them on a regular basis.

## Decision
Since we do not want to redeploy the ExecutorPool everytime the available Executors change,
Executors and the ExecutorPool must be implemented as separate components.

## Consequences
We need to enable communication between the ExecutorPool and Executors so that Executors can be dynamically
added and removed.
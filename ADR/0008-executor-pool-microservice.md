# 8. Executor Pool Microservice

Date: 2021-09-28

## Status

Accepted

Supercedes [4. Executor Pool Microkernel Microservices Architecture](0004-executor-pool-microkernel-microservices-architecture.md)

## Context

We need to extend the executor pool on a regulary basis.
## Decision

We implement the pool as a microservice, since a more complex architecture like microcernel would be overengineered for a lookup service.
## Consequences

We spare the effort to implement a common interface for the microcernel system.

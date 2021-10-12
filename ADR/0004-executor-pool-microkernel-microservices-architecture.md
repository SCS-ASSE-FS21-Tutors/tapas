# 4. Executor Pool Microkernel Microservices Architecture

Date: 2021-09-27

## Status

Superceded by [10. Executor Pool Microservice](0010-executor-pool-microservice.md)

## Context

The executor pool needs to be able to adapt to extension and removal of executors at runtime. It should additionally be available regardless of the amount of available executors

## Decision

The architectural style for the executor pool will be a microkernel microservices hybrid architecture.

## Consequences

Executors will need to be realized as microservices. 
Executors can be added and removed to and from the pool without redeployment.
Executor pool need to keep track of existing microservices. 

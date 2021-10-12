# 9. Service Granularities

Date: 2021-09-27

## Status

Accepted

## Context

In order to figure out a system architecture combining the different bounded contexts identified in the event storming session, we need to define cohesive system components introducing low coupling.

## Decision

We decided for the following components:
- Tasklist Integrator: Data Relationship to Roster 
- Roster Integrator: Data Relationship to Tasklist 
- Executor 
- Executor Pool 
- Auction House

We decided on these components based on the disintegrators service functionality, scalability / throughput and fault tolerance collectively. This can be argued by the components dealing with different tasks required to operate on different scales.

## Consequences

We need to manage the state of the roster.

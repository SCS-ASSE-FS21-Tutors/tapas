# 12. Coordination Style

Date: 2021-09-27

## Status

Accepted

## Context

We are building a multi-component system which needs to handle a workflow among all components. Therefore, we need to consider how to coordinate such workflows among components.

## Decision

Our systems coordination is based on choreography, since our most important characteristics (responsiveness, fault tolerance, scalability) are supported by that style.


## Consequences

Since we have no orchestrator in place, the communication needs to be coordinated by the components themselves. Therefore, services are coupled more heavily.
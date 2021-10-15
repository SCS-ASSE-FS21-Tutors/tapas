# 12. Coordination Style

Date: 2021-10-12

## Status

Accepted

## Context

We are building a multi-component system which needs to handle a workflow among all components. 
Therefore, we need to find a way of coordinating the communication between those components. 
Especially, we need to decide if we want to establish a dedicated component taking care of distributing information or
make the components handle the flow of information themselves.

## Decision

Our systems coordination is based on choreography, since our most important characteristics 
(responsiveness, fault tolerance, scalability) are supported by that style. 


## Consequences

A choreographed architecture spares a central component taking care of the information distribution. Therefore, the
components need to take care of sharing the necessary data themselves. This leads to an increased complexity implementing
the components. Moreover, there is no central entity to keep track of states. In terms of data consistency, it is crucial
that the components are able to synchronise their states.
# 1. communication

Date: 2021-09-27

## Status

Accepted

## Context

We need different heterogenous bounded contexts to work togther.
## Decision
One part of the architectural decission is to use event driven architecture. This is because among  our most important nonfunctional requirements are interoperability and workflow. This implies the communication between the different components upon certain events.


## Consequences
We need to set up the event pipelines for the different components to listen and react. 

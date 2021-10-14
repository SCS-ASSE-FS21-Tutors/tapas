# 8. No monolithic user interface

Date: 11.10.2021

## Status

Accepted

## Context

The user interface is not necessary for all microservices. In general, it is not required to have any user interface.
Also, the monolithic UI introduces coupling among Microservices, which contradicts the original idea of Microservices.

## Decision

User interfaces are created when necessary, and only for selected Microservices. Furthermore, there will be no UIs for
multiple Microservices.

## Consequences

Less complexity with no unified user interface, perhaps individual, smaller UIs.
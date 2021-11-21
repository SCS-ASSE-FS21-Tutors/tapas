# 19. Data ownership

Date: 2021-11-21

## Status

Accepted

## Context

As seen in the lecture it is advisable that the data ownership is clearly regulated and that a service is the data owner of a table it writes to. If multiple services write to the same table things may get complicated.

## Decision

As a result, and our approach to keep things as simple as possibe, clear data ownership and hence allocating a table to a service is a goal of our approach. We have decided to add the tables to the respective bounded contexts of a service and having a clear data ownership structure.

## Consequences

Some additional communication between services may result from this decision as a single service is responsible for writing to its table, however this results in many advantages and also simplifies the data persistence implementation.
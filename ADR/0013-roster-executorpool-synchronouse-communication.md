# 12. Roster ExecutorPool synchronouse communication

Date: 2021-10-12

## Status

Accepted

## Context

As elaborated in ADR #10 asynchronouse communication is used among most of the components due to the partially long
execution times and the resulting component coupling. Furthermore, we elaborated that we use synchronous
communication wherever feasible to decrease complexity of implementation.
## Decision

We decided to use synchronous communication between the Roster and the ExecutorPool. This is due to the simplicity 
of this information exchange resulting from the separation of concerns. The Roster assigns the tasks based on the
executors registered in the ExecutorPool. To do so, it just needs to query the currently available Executors.
This is a straightforward process, not introducing the necessity of asynchronous communication.

## Consequences

By doing so, we are able to reduce the implementation and communication complexity in this part of the application.
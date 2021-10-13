# 12. Add Executor No Event

Date: 2021-09-27

## Status

Accepted

## Context

In ADR #1 we stated that our system is based on events. Therefore, every major process in our system emits an event,
which may be processed by its listeners. Nevertheless, there may be cases, where it is superfluent to emit events, since
the process is not relevant for the rest of the system. This must be reasoned.

## Decision

We decided not to emit an event if an executor is added to the ExecutorPool. It is irrelevant, since the Roster queries
anyways for all available Executors prior to the assignment process. Furthermore, a new Executor is just relevant 
if there is a corresponding task. If this case never occurs, there is no need to inform other components about its 
existence.

## Consequences

By avoiding the event propagation, we are able to reduce the communication complexity in this part of the system.
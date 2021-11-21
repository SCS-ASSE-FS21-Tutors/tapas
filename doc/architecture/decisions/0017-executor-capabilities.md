# 17. Executor capabilities

Date: 2021-11-21

## Status

Accepted

## Context

This ADR updates the 0008 and 0009 ADRs, since they are outdated.

## Decision

Update on 0008 from 2021-10-17:
- We mentioned that we differentiate between internal and external executors. At this point in time we intended to add external Executors (Executors form other Tapas applications) to our Executor Pool - if they offer capabilities that our Executors do not offer. Since external Executors are able to update the Task status directly in our Task List, we don't have to add them to our Executor Pool anymore.
- In the consequences we mentioned that we have to back propagate status updates form the executors to through the Executor Pool to the Roster. This communication has become obsolete, because we know directly update the status of Tasks from the Executor Pool two the Task List. This reduces and simplifies the communication in our application.

Update on 0009 from 2021-10-17:
- In the decision we mentioned that an executor registers himself in the Executor Pool. This functionality is not implemented. We decided to hard-wire the executors to the Pool. This does not limit the functionality of the application.

## Consequences

These changes reduce communication and simplify the application.
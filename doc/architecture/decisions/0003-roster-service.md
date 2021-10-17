# 3. Roster service

Date: 2021-09-29

## Status

Accepted

## Context

The Roster Service finds a suitable internal Executor based on their capabilities to match a given Task and finally assigns the Executor to the Task. The matching is done by communicating with the Executor Pool Service. If no internal Executor can be found the Roster Service communicates with the Auction Service to find an external Executor.

## Decision

We will create a Roster Service, which handles the assignment of Tasks to Executors. The Roster Service has its own database to store information about the assignments and the work in progress Tasks.

## Consequences

The separation from the other services guarantees availability, scalability and fault-tolerance. If the Roster Service has any failure, Tasks and Executors can still be managed through the other services.

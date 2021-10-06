# 5. Consolidated Roster and Executor Pool

Date: 2021-09-27

## Status

Proposed

## Context

The Roster and Executor pool share much data such as Tasks, Executors or assignments. Depending on the state of the Task, it is present in multiple bounded contexts.

## Decision

Consolidate Roster and Executor Pool in a Event-based subsystem.
As most of the data between the three bounded contexts is shared, it makes sense to store it centrally. For communication among the two contexts, Events are used. Events only inform another context about a state-change, but contain no information, as this is in the shared database.
As operations in this context are synchronous, e.g. the assignment of a Task to a local Executor in the Executor pool, more separation is not required.
Still, to satisfy the Single Responsibility Principle, no further merging is intended.

## Consequences

Introduction of Event Channel(s) and connecting the Roster and Executor pool to them. Also, interfaces to send and receive Events must be implemented for the two contexts.

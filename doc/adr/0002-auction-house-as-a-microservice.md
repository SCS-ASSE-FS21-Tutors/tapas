# 2. Auction House as a microservice

Date: 2021-09-27

## Status

Accepted

## Context

After the Roster selected and prompted an Executor to carry out a task, the Executor asynchronously operates on its own. After it finished the task, the Executor returns the result and is ready again for another task.

## Decision

As Executors may fail, the remainder of the system, including other Executors should still be operational.
As Executors may take a long time to complete a task, they run asynchronously, such that the Roster can assign further tasks to other Executors.
Tasks typically contain data, which is processed or used by an Executor. The integrity and consistency of this data is important, and as Executors may fail, assigning an own database to each Executor ensures that at maximum, the data given to the Executor is lost. Data in the original task, or data from other Executors is preserved.

## Consequences

When a new Executor is registered, a database will be provisioned for it.
# 11. TaskList Roster HTTP Push Communication

Date: 2021-10-12

## Status

Accepted

## Context

The TaskList is responsible for keeping track of tasks and the Roster for assigning them to an Executor. 
Therefore, we need a way of propagating newly created tasks to the Roster to have them assigned.
Generally there are two ways to do this.
1) Push: The TaskList pushes the new tasks to the Roster upon creation.
2) Pull: The Roster pulls newly created tasks from the TaskList once it is free.

## Decision

We decided to use the push communication, because we want to separate the concerns.
Therefore, everything related to the task assignment should be encapsulated in one component.
If we used a pull system, we would keep track of the unassigned tasks in the TaskList.
We consider this as part of the assignment process and therefore a job for the Roster.
The communication process is based on HTTP.
The Roster publishes an event upon a (un)-successful assignment, which is processed by the TaskList.

## Consequences

The Roster needs to be able to handle multiple incoming tasks.
Due to the fact that assigning the Executor can take time (e.g.AuctionHouse), the workflow
is asynchronous.
The TaskList should listen on the events published by the Roster, to keep track of the Task status.



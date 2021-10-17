# 7. Communication between Task List and Roster

Date: 2021-10-17

## Status

Accepted

## Context

The Roster has to be notified if a new Task is created. He has the responsibility to either forward a Task to the Auction Service or the Executor Pool.

## Decision

It is crucial to notify the Roster if a new Task is added to the Task List. Adding a Task to the Task List triggers a task created event.

## Consequences

An event handler has to be implemented in the Roster microservice. As soon as a new Task is added to the Task List, an event is published and the event handler in the Roster microservice handels the new event.

# 13. Executor updates Task List

Date: 2021-11-14

## Status

Accepted

## Context

Task status need to be updated in the Task List. A Task can have the open, assigned, running or executed status. It is especially important to know if a task was executed.

## Decision

We need to update the status of a Task when the execution is finished. We decided that the Executor himself updates a Task List when he finishes the execution of a Task.

## Consequences

An Executor must be able to update a Task List, indifferent from where he got the Task in the first place.

# 7. Roster Recoverability

Date: 2021-09-27

## Status

Accepted

## Context

If the roster is down, we still need to be able to access the information about task, executor assignment.
## Decision

We need to manage a persistent state for the roster in an independent database, to be able to recover it upon failure.
## Consequences

We need to manage the state of the roster.

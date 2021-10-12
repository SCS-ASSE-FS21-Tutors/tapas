# 3. Roster-Task Service based

Date: 2021-10-12

## Status

Superseded by [11. TaskList Roster HTTP Push Communication](0011-tasklist-roster-http-push-communication.md)

(TODO: Discuss in group. I think information sharing is covered by 11.)


## Context

We need to support the assignment of Tasks to Executors. This is done by the Roster.
Consequently, they need to share some information / data about tasks and corresponding executors.

## Decision

Tasklist and Roster components will compose a service based architecture in order for them to share the same database. 

## Consequences

By doing so, we feature data integrity, which we identified as one of the most important aspects in this component.
Furthermore, we support ACID based data handling.

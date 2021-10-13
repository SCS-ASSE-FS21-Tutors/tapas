# 3. Roster-Task Service based

Date: 2021-10-12

## Status

Accepted


## Context

We need to support the assignment of Tasks to Executors. This is done by the Roster.
Consequently, they need to share some information / data about tasks and corresponding executors.
Since tasks have different states which need to be managed by the Roster and displayed by the TaskList
it would be beneficial to have a shared database.

## Decision

TaskList and Roster components will compose a service based architecture in order for them to share the same database.

## Consequences

By doing so, we feature data integrity, which we identified as one of the most important aspects in this component.
The database must be able to handle concurrent queries.
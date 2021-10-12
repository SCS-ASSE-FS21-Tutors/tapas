# 3. Roster-Task Service based

Date: 2021-09-27

## Status

Accepted

## Context

We need to support the assignment of tasks to executors happening in the roster. Consequently, they need to share the same information / data about tasks and corresponding executors.
## Decision

Tasklist and Roster components will compose of features of a  service based architecture in order for them to share the same database. 
## Consequences

By doing so, we feature data integrity, which we identified as one of the most important aspects in this component. Furthermore, we support ACID based data handling.

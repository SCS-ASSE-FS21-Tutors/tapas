# 10. Integrate Executor Pool into Roster

Date: 2021-10-14

## Status

Accepted

## Context

The Executor Pool is essentially a list containing Executors which are run locally (that is, on the group's server).

This ADR partially replaces ADR 6 (regarding separate Executor Pool and Roster).

## Decision

The Executor Pool is integrated into the Roster. This way, if the Roster requires an Executor for the pool, it can
"internally" seek an Executor in the Executor Pool instead of sending out a request. As this procedure takes little time,
it can be completed synchronously.
Prior, it was discussed to separate the Executor Pool because it is functionally different from the Roster
(it operates with local Executors, while the Roster operates with Tasks and local and external Executors).
However, this introduces the following drawbacks:
1. By separating the two, many classes would need to be duplicated, increasing initial and maintenance effort.
2. The Executor Pool is coupled to the Roster, meaning that separating the two augments external coupling.
3. Latency is increased as more communication is facilitated externally
4. Further move towards partial consistency, as Executors in the Executor Pool are not necessarily synchronous with Executors
in the Roster.

Furthermore, the local Executor Pool is not used by other groups (i.e. externally), neutralising the requirement of better
external availability.

## Consequences

The Roster incorporates the Executor Pool's functionality. The Executor Pool Microservice can be deleted and communication
between Roster and Executor Pool is simplified.
# 12. Design Decision Communication

Date: 2021-10-17

## Status

Accepted

## Context

As soon as a new Task is submitted to the Task List Service the Roster Service will be notified, which forwards a newly created task to the Executor Pool which then searches for a suitable Executor. If no Executor can be found the Roster Service will be notified and an external Executor search will be performed.

## Decision

Our architecture allows a choreographic communication between services. Therefore we can avoid a single point of failure. On the other hand the communication between the Executor Pool and the Executor is orchestrated by the Executor Pool . Therefore we are utilizing an hybrid approach.


## Consequences

Complexity of the whole architecture increases, since we are using both communication styles.

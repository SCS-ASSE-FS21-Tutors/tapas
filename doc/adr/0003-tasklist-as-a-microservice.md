# 3. Tasklist as a microservice

Date: 2021-09-27

## Status

Proposed

## Context

After a user has created a Task, it is stored in the Tasklist. The Tasklist forwards it to match it with an Executor, but even after completion, the Task is contained in the Tasklist, except it is deleted.

## Decision

It is required that the performance of the platform only degrades gracefully under heavy traffic. As the Tasklist is the principal inlet of traffic, separating it as microservice thus allows it to scale independently from the remaining system.
It is furthermore assigned a dedicated database, with two goals in mind: Firstly, the dedicated database only serves the Tasklist and must not share it's capacity with another context. Secondly, the remaining system is not infringed if the database is under heavy load.
There are no concerns about the latency between the Tasklist and the connected contexts (e.g. the Roster), as it is sufficient when a Task is forwarded with some delay (i.e. in the order of several seconds). The same applies for the Roster updating the Tasklist.

## Consequences

The Tasklist can be assigned more resources when expecting heavy traffic. Also, the remaining system is partially "shielded" from many client requests.
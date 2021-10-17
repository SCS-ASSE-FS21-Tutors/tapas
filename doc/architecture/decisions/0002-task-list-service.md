# 2. Task List service

Date: 2021-09-29

## Status

Accepted

## Context

Tasks are added to the Task List at the time they are created. The Task List Domain communicates with the Roster Domain for the execution of the Task. Assigned Tasks are transferred to the Roster Service.

## Decision

The system has to be able to handle thousands of Tasks. Therefore it has to be secured that the Task List is highly scalable. Additionally, enough throughput has to be available for the creation of this high number of Tasks.

## Consequences

A standalone service for the Task List fulfils the scalability & throughput requirements.

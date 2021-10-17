# 4. Seperate service for executor pool

Date: 2021-10-18

## Status

Accepted

## Context

The Executor pool keeps track of the connected executors and their purpose and status.

## Decision

We will have a separate service for the executor pool.
There are no other domains which share the same or similar functionality.
The executor pool also scales differently than other services.

## Consequences

Having the executor pool as a separate service will help with the deployability of this service but will make the overall structure more complex and reduces testability.

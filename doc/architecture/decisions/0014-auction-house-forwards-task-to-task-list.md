# 14. Auction House forwards Task to Task List

Date: 2021-11-14

## Status

Accepted

## Context

The Auction House bids on auctions from other Auction Houses. In case the Auction House wins the bid, it must be able to forward the Task.

## Decision

The Auction House forwards the Task to the Task List where the Task gets forwarded to the Roster which then checks for an Executor in the Executor Pool.

## Consequences

The Auction House must be able to communicate to the Task List and forward the Task.

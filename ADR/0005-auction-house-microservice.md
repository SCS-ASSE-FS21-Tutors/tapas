# 5. Auction House Microservice

Date: 2021-10-12

## Status

Accepted

## Context
Our application needs to support bidding for the execution of task by foreign executors and receiving bids for
the Execution of foreign tasks. This functionality is the responsibility of the AuctionHouse. 

## Decision
For this to work, we need the AuctionHouse to be available and to respond to bidding events.
Therefore, the AuctionHouse will be part of an event based architecture.

## Consequences
The AuctionHouse needs to be able to react to incoming events and notifies the respective executors.
Executors need to expose interfaces on which they can be notified by the AuctionHouse.

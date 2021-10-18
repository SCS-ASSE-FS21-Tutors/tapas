# 5. Event driven communication

Date: 2021-10-18

## Status

Accepted

## Context

Services need to be able to communicate with each other. Services need to be scalable and therefore multiple services will need to get the same messages. Most of the processes are about responding to events that are happening throughout the system.

## Decision

We will use mainly event driven communication.

## Consequences

Event driven communication will help use to create a system which has high scalability and elasticity. Through persisting messages, we will also reach way higher fault tolerance and recoverability.
Having an event driven communication, we can only guarantee eventual consistency.

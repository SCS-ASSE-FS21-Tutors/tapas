# 10. Design Decision Task Structure

Date: 2021-10-17

## Status

Accepted

## Context

Currently, our Tapas application is able two execute two kind of Tasks. A Robot Task and a simple Calculation Task. Adding a payload to the Task class gives us the possibility to customize requests and therefore more flexibility.

## Decision

We have added a payload property to the Task entity.

## Consequences

A new attribute - namely payload -  is needed. Since the Task entity is used in all microservices - adjustments have to be made in all services. The payload does not have to be hard-coded anymore.

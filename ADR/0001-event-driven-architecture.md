# 1. Event Driven Architecture

Date: 2021-10-12

## Status

Accepted

## Context

One of our most important nonfunctional requirements is workflow. 
With each workflow multiple components are involved (e.g. task assignment).
Therefore, it is essential that different components of the application can interact with, 
as well as react to each other.
The execution time of some steps within the workflow is not fixed.
Therefore, we need to inform other components upon completion.

## Decision
Events are a suitable method to keep track of completed workflow steps.
Consequently, we use an event driven architecture approach.
TODO: Document how to implement events.

## Consequences

The complexity of our system increases, since we do not get immediate response upon a 
request and since multiple components react upon events.
To ensure the applications fault tolerance, we need a more elaborate 
error handling since we need to deal with delayed or missing responses.

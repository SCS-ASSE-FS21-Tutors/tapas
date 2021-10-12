# 10. Asynchronous Communication

Date: 2021-09-27

## Status

Accepted

## Context

We are building a multi-component system which needs to handle a workflow among all components. Therefore, we need to consider runtime component coupling.

## Decision

Our components need to frequently communicate with each other. Therefore, quantum entanglements may occur. To build a decoupled system upon that, we use async communication between the components.

## Consequences

Since we use async communication, the effort for building the system may be higher.

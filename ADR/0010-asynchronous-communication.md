# 10. Asynchronous Communication

Date: 2021-10-12

## Status

Accepted

## Context

We are building a multi-component system which needs to handle a workflow among all components.
During runtime, the components consequently need to share various information which is needed to carry out the tasks.
In this context we need to consider temporary entanglement of our architectural quanta during runtime.
Those can introduce dynamic coupling of components.

## Decision

Our components need to frequently communicate with each other. If those communication processes take place sychronously,
the described entanglement of architectural quanta occurs. Consequently, we use asynchronouse communication,
wherever it is necessary. This refers especially to tasks with a higher execution duration (e.g. Robot Executor).
The dynamic component coupling is especially relevant in this cases, since the components are coupled for the whole 
duration of the execution.

## Consequences

Asynchronouse communication introduces more overhead. Therefore, the system is more complex to build.
Also it effects the error handling, since we need to deal with delayed or missing responses.
To counteract, we consider to implement synchronouse communication wherever it is feasible.
# 18. Executor base service

Date: 2021-11-21

## Status

Accepted

## Context

The two Executor Services rely a lot on the same functionalities. This leads to a lot of code duplication.

## Decision

Our Executor Base Service bundles functionalities that are both used in the Robot Executor and in the Computation Executor. We use this Service to manage changes in the state of Task and Executors and to register Executors to the pool. This services reduces our code and simplifies the application.

## Consequences

The functionalities must be outsourced to the new service.

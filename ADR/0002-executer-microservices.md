# 2. Executor Microservices

Date: 2021-10-12

## Status

Accepted

## Context
Our application must be able to support multiple Executors which have different properties 
(i.e., certain tasks can only be completed by certain executors).
Therefore, we need to separate different executor functionalities to work independently.

## Decision
Separating the responsibilities of multiple Executors will be done by implementing multiple Executors as microservices.

## Consequences
Each executor has its own data environment. Synchronizing data between executors would become difficult.

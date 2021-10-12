# 11. ACID Transactions

Date: 2021-09-27

## Status

Accepted

## Context

We are building a multi-component system which needs to handle a workflow among all components. Therefore, we need to consider access to shared data sources.


## Decision

To ensure data integrity, recoverability and interoperability, we need to support ACID transactions.


## Consequences

Use a ACID compliant databases like PostgresDB whenever dealing with multiple DB users.

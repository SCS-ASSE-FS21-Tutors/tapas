# 7. Roster and TaskList Recoverability

Date: 2021-10-12

## Status

Accepted

## Context

Should the Roster or the TaskList ever fail, we need to be able to access the information about task,
executor assignment.

## Decision
We save data which needs to be recovered upon possible failure in a database.

## Consequences
When starting the Roster or TaskList the database needs to be searched by those components for already existing data.
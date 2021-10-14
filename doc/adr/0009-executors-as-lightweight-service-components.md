# 9. Executors as lightweight service components

Date: 14.10.2021

## Status

Accepted

## Context

Until now, Executors were designed either as Microservices with separate data storage or running inside a single Executor hub.
Executors, however, seldom require a persistent data storage and contain few modules. Still, they need to be
run independently and have an own interface.
This ADR replaces ADR 7.

## Decision

Instead of implementing Executors as fully-fledged Microservices, we implement them as lightweight service components.

Although each Executor is its own _independently deployable component_, it usually has no persistent data storage
(e.g. database) attached and has fewer modules than a Microservice. This is motivated by two reasons:

1. having an attached data storage and a complex internal module structure makes the Executor difficult to deploy and
to maintain. The external data storage would additionally contribute to eventual consistency (instead of immediate).
Despite this drawback, no large gains in partition safety were to be expected as there is little data attached to an Executor.
2. It could be argued that multiple lightweight Executors could be run inside an _independently deployable unit_, (introduced as hub)
and for example be implemented as classes, implementing an Interface for standardising the Executor's methods. This would however introduce two problems:
   1. to make the Executors run simultaneously (e.g. one Executor drives a robot while the other has to print a document),
   a form of concurrency is required. If an Executor fails, it may never yield the control flow and block all other Executors,
   which violates fail-safety. Without concurrency, the running Executor would block all other Executors. An Executor
   requiring hours for a task could then block Executors requiring only several seconds.
   2. Executors are later used by other groups. As those groups need access to the Executor, it is simpler if each Executor
   has its own URI. Otherwise, a routing mechanism would need to be devised to dispatch requests to the respective Executor.

## Consequences

No persistent data storage must be deployed for each Executor, nor a central Executor hub must be created.
Also, each Executor has a simplified internal design, requiring only one to two classes inside one package instead of
tens of classes in several packages. This also allows a quick setup of Executors.
Standardisation of methods among Executors is important for using Executors among several groups.
Because standardisation of Executor's methods is not possible with language constructs as Interfaces, it must be
enforced externally. For this, a documentation in the project's Wiki was set up, and must now be written and exchanged
with other groups.
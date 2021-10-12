# 9. Service Granularities

Date: 2021-10-12

## Status

Accepted

## Context

The application needs to deal with multiple heterogenous tasks. Therefore, different components are introduced, 
to keep the different concerns separated from each other.
Each component reflects one of the bounded contexts we identified in the event storming. Those components need to be
cohesive and should introduce low coupling.

## Decision

We decided for the following components:
- TaskList
- Roster
- Executor 
- Executor Pool 
- Auction House

Integrators:
  The Roster and the TaskList have a data relationship. The roster needs to know about new tasks to be able to assign them.
The TaskList needs to know about the status of a task during assignment to keep track of it.

Disintegrators:
Each component has a very different task. Consequently, there are various disintegrators enumerated with an example below.
- Service functionality: The Roster exclusively assigns tasks, while the ExecutorPool only keeps track of available Executors
- Scalability/Throughput: The Roster needs to deal with every incoming Task while an Executor might stay idle for a 
longer time.
  
- Fault Tolerance: If the Roster crashes, no Tasks can be assigned anymore. In terms of fault tolerance this is more 
critical than e.g. an Executor, since a failure leads just to a share of tasks which can not be executed.
  
## Consequences

Due to the aforementioned aspects, it is essential to separate the concerns within the system as elaborated.
Nevertheless, this also introduces increasing complexity in terms of communication. Since the system basically 
processes a workflow, it is inevitable that the components share information. This introduces additional overhead.

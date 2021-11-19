# 16. Code sharing

Date: 2021-11-19

## Status

Accepted

## Context

In our Tapas application we have different services that use the same code. Options are to use either a shared service or a shared library.

## Decision

We decided to use a shared service for our tapas application. The main reason we decided us for a shared service instead of a shared library is that we have a high code volatility. After handing in the first two assignments we noticed that we had to adjust our code to fulfill the requirements for the application. Using a shared library minimizes these adjustments. Additionally, we have the advantage that the bounded context is preserved.

## Consequences

In our common service we outsourced functionalities that are used by several other services. Task and Executor classes and representations are located in this service. Besides, we also manage the addresses of our services in a dedicated class. This enables us the switch easier to our local addresses. Additionally, we have a Webclient class which simplifies the use of HTTP request methods in our adapters. Since the SelfValidating class is also used in all of our services we externalized this class to the common service as well.
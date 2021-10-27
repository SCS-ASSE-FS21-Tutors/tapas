# TAPAS
This is the main GitHub project for your implementation of the TAPAS application.

Start-up locally: `bash build-all.sh && docker-compose -f docker-compose.local.yml -f docker-compose.yml up`

## Project Structure
This project is structured as follows:
* [tapas-tasks](tapas-tasks)
* [tapas-roster](tapas-roster)
* [tapas-executor-pool](tapas-executor-pool)
* [tapas-executor-1](tapas-executor-1)
* [tapas-executor-1](tapas-executor-2)
* [tapas-auction-house](tapas-auction-house)

## How to Deploy on your VM
1. Start your Ubuntu VM on Switch.
   * VM shuts down automatically at 2 AM
   * Group admins can do this via https://engines.switch.ch/horizon
2. Push new code to the *main* branch
   * Check the status of the workflow on the *Actions* page of the GitHub project
   * We recommend to test your project locally before pushing the code to GitHub. The GitHub Organizations
    used in the course are on a free tier plan, which comes with [various limits](https://github.com/pricing).
3. Open in your browser `https://app.<server-ip>.nip.io`

For the server IP address (see below), you should use dashes instead of dots, e.g.: `127.0.0.1` becomes `127-0-0-1`.

## VM Configurations

Specs (we can upgrade if needed):
* 1 CPU
* 2 GB RAM
* 20 GB HD
* Ubuntu 20.04

| Name | Server IP |
|-------|-----------|
|SCS-ASSE-VM-Group3|86.119.34.242|

## Architecture Decision Records

Stored as PDF file for convenience.
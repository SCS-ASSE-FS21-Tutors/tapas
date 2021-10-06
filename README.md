# TAPAS

This is the main GitHub project for your implementation of the TAPAS application.

## Run application in developent

We use Docker & docker-compose in development to easly start all the microservices and other needed application (db's, message-broker's) at once. All microservices have hot-reloads enabled by default!

#### Start

```
docker-compose up
```

#### Rebuild container

```
docker-compose up --build
```

#### Start detached

```
docker-compose up -d
```

#### Stop detached

```
docker-compose down
```

## Available Services

Ports and debug ports of each service are listed below:

| Name               | Port | Debug Port |
| ------------------ | ---- | ---------- |
| Tasklist           | 8081 | 5005       |
| Assignment Service | 8082 | 5006       |
| Executor Pool      | 8083 | 5007       |
| Executor 1         | 8084 | 5008       |
| Executor 2         | 8085 | 5009       |

## Project Structure

This project is structured as follows:

- [tapas-tasks](tapas-tasks): standalone project for the Tapas-Tasks micro-service (Spring Boot project)
  - [tapas-tasks/src](tapas-tasks/src): source code of the project (following the Hexagonal Architecture)
  - [tapas-tasks/pom.xml](tapas-tasks\pom.xml): Maven pom-file
- [app](app): folder as placeholder for a second micro-service (Spring Boot project)
- [docker-compose.yml](docker-compose.yml): Docker Compose configuration file for all services
- [.github/workflows/build-and-deploy.yml](.github/workflows/build-and-deploy.yml): GitHub actions script (CI/CD workflow)

## How to Add a New Service with Spring Boot

### Create a new Spring Boot project

- Recommended: use [Spring Initialzr](https://start.spring.io/) (Maven, Spring Boot 2.5.5, Jar, Java 11, dependencies as needed)
- Set the Spring application properties for your service (e.g., port of the web server) in `src/resources/application.properties`

### Update the Docker Compose file

Your TAPAS application is a multi-container Docker application ran with [Docker Compose](https://docs.docker.com/compose/).
To add your newly created service to the Docker Compose configuration file, you need to create a new service
definition in [docker-compose.yml](docker-compose.yml):

- copy and edit the `tapas-tasks` service definition from [lines 29-42](https://github.com/scs-asse/tapas/blob/424a5f5aa2d6524acfe95d93000571884ed9d66f/docker-compose.yml#L29-L42)
- change `command` (see [line 31](https://github.com/scs-asse/tapas/blob/main/docker-compose.yml#L31))
  to use the name of the JAR file generated by Maven for your service
  - note: if you change the version of your service, you need to update this line to reflect the change
- update the Traefik label names to reflect the name of your new service (see [lines 37-42](https://github.com/scs-asse/tapas/blob/424a5f5aa2d6524acfe95d93000571884ed9d66f/docker-compose.yml#L37-L42))
  - e.g., change `traefik.http.routers.tapas-tasks.rule` to `traefik.http.routers.<new-service-name>.rule`
- update the Traefik `rule` (see [line 37](https://github.com/scs-asse/tapas/blob/424a5f5aa2d6524acfe95d93000571884ed9d66f/docker-compose.yml#L37)) with the name of your new service: `` Host(`<new-service-name>.${PUB_IP}.nip.io`) ``
- update the Traefik `port` (see [line 39](https://github.com/scs-asse/tapas/blob/424a5f5aa2d6524acfe95d93000571884ed9d66f/docker-compose.yml#L39)) with the port configured for your new service

### Update the GitHub Actions Workflow

This project uses GitHub Actions to build and deploy your TAPAS application whenever a new commit is
pushed on the `main` branch. You can add your new service to the GitHub Actions workflow defined in
[.github/workflows/build-and-deploy.yml](.github/workflows/build-and-deploy.yml):

- copy and edit the definition for `tapas-tasks` from [line 28-30](https://github.com/scs-asse/tapas/blob/424a5f5aa2d6524acfe95d93000571884ed9d66f/.github/workflows/build-and-deploy.yml#L28-L30)
- update the `mvn` command used to build your service to point to the `pom.xml` file of your new service (see [line 29](https://github.com/scs-asse/tapas/blob/424a5f5aa2d6524acfe95d93000571884ed9d66f/.github/workflows/build-and-deploy.yml#L29))
- update the `cp` command to point to the JAR file of your new service directive (see [line 30](https://github.com/scs-asse/tapas/blob/424a5f5aa2d6524acfe95d93000571884ed9d66f/.github/workflows/build-and-deploy.yml#L30))
  - note you will need to update the complete file path (folder structure and JAR name)

### How to Run Your Service Locally

You can run and test your micro-service on your local machine just like a regular Maven project:

- Run from IntelliJ:
  - Reload _pom.xml_ if necessary
  - Run the micro-service's main class from IntelliJ for all required projects
- Use Maven to run from the command line:

```shell
mvn spring-boot:run
```

## How to Deploy on your VM

1. Start your Ubuntu VM on Switch.
   - VM shuts down automatically at 2 AM
   - Group admins can do this via https://engines.switch.ch/horizon
2. Push new code to the _main_ branch
   - Check the status of the workflow on the _Actions_ page of the GitHub project
   - We recommend to test your project locally before pushing the code to GitHub. The GitHub Organizations
     used in the course are on a free tier plan, which comes with [various limits](https://github.com/pricing).
3. Open in your browser `https://app.<server-ip>.nip.io`

For the server IP address (see below), you should use dashes instead of dots, e.g.: `127.0.0.1` becomes `127-0-0-1`.

## VM Configurations

Specs (we can upgrade if needed):

- 1 CPU
- 2 GB RAM
- 20 GB HD
- Ubuntu 20.04

| Name               | Server IP     |
| ------------------ | ------------- |
| SCS-ASSE-VM-Group1 | 86.119.35.40  |
| SCS-ASSE-VM-Group2 | 86.119.35.213 |
| SCS-ASSE-VM-Group3 | 86.119.34.242 |
| SCS-ASSE-VM-Group4 | 86.119.35.199 |
| SCS-ASSE-VM-Group5 | 86.119.35.72  |

## Architecture Decision Records

We recommend you to use [adr-tools](https://github.com/npryce/adr-tools) to manage your ADRs here in
this GitHub project in a dedicated folder. The tool works best on a Mac OS or Linux machine.

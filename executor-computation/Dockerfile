FROM openjdk:11 AS development

WORKDIR /opt/app

# ENV SPRING_DATASOURCE_URL=jdbc:mysql://backend-db:3306/db

COPY .mvn/ .mvn
COPY mvnw pom.xml mvnw.cmd ./

RUN apt-get clean && apt-get update && apt-get install dos2unix
RUN dos2unix mvnw

RUN ./mvnw dependency:go-offline

COPY src /opt/app/src
COPY *target /opt/app/target

CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.jvmArguments=\"-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005\"", "-Dspring.devtools.restart.enabled=true"]

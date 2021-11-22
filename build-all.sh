mkdir ./data

set -e #Break on first error 
mvn $1 -f tapas-tasks/pom.xml --batch-mode --update-snapshots verify
mvn $1 -f tapas-roster/pom.xml --batch-mode --update-snapshots verify
mvn $1 -f tapas-executor-pool/pom.xml --batch-mode --update-snapshots verify
mvn $1 -f tapas-executor-1/pom.xml --batch-mode --update-snapshots verify
mvn $1 -f tapas-executor-2/pom.xml --batch-mode --update-snapshots verify
mvn $1 -f tapas-auction-house/pom.xml --batch-mode --update-snapshots verify

cp ./tapas-tasks/target/tapas-tasks-0.0.1-SNAPSHOT.jar ./data  
cp ./tapas-roster/target/tapas-roster-0.0.1-SNAPSHOT.jar ./data
cp ./tapas-executor-pool/target/tapas-executor-pool-0.0.1-SNAPSHOT.jar ./data
cp ./tapas-executor-1/target/tapas-executor-1-0.0.1-SNAPSHOT.jar ./data
cp ./tapas-executor-2/target/tapas-executor-2-0.0.1-SNAPSHOT.jar ./data
cp ./tapas-auction-house/target/tapas-auction-house-0.0.1-SNAPSHOT.jar ./data

# docker-compose up
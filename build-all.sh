mkdir ./data

mvn -f tapas-tasks/pom.xml --batch-mode --update-snapshots verify
mvn -f tapas-roster/pom.xml --batch-mode --update-snapshots verify
mvn -f tapas-executor-pool/pom.xml --batch-mode --update-snapshots verify
mvn -f tapas-executor-1/pom.xml --batch-mode --update-snapshots verify
mvn -f tapas-executor-2/pom.xml --batch-mode --update-snapshots verify

cp ./tapas-tasks/target/tapas-tasks-0.0.1-SNAPSHOT.jar ./data  
cp ./tapas-roster/target/tapas-roster-0.0.1-SNAPSHOT.jar ./data
cp ./tapas-executor-pool/target/tapas-executor-pool-0.0.1-SNAPSHOT.jar ./data
cp ./tapas-executor-1/target/tapas-executor-1-0.0.1-SNAPSHOT.jar ./data
cp ./tapas-executor-2/target/tapas-executor-2-0.0.1-SNAPSHOT.jar ./data

cp docker-compose.yml ./data/docker-compose.yml

# cd data
# docker-compose up
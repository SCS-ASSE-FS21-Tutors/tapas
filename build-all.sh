mkdir ./data

mvn -f tapas-tasks/pom.xml --batch-mode --update-snapshots verify
cp ./tapas-tasks/target/tapas-tasks-0.0.1-SNAPSHOT.jar ./data  

mvn -f app/pom.xml --batch-mode --update-snapshots verify
cp ./app/target/app-0.1.0.jar ./data

cp docker-compose.yml ./data/docker-compose.yml
chmod 777 data/*

# cd data
# docker-compose up
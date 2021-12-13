package ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<MongoTaskDocument,String> {

    MongoTaskDocument findByTaskId(String taskId);
}

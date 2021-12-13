package ch.unisg.tapasexecutorpool.pool.adapter.out.persistence.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutorRepository extends MongoRepository<MongoExecutorDocument,String> {

    MongoExecutorDocument findByExecutorId(String taskId);
}

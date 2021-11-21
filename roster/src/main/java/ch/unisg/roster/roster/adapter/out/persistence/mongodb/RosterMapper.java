package ch.unisg.roster.roster.adapter.out.persistence.mongodb;


import ch.unisg.common.valueobject.ExecutorURI;
import ch.unisg.roster.roster.domain.RosterItem;
import org.springframework.stereotype.Component;

@Component
public class RosterMapper {

    RosterItem maptoDomainEntity(MongoRosterDocument rosterItem) {
        return new RosterItem(rosterItem.taskId, rosterItem.taskType,
            new ExecutorURI(rosterItem.executorURI));
    }

    MongoRosterDocument mapToMongoDocument(RosterItem rosterItem){
        return new MongoRosterDocument(
            rosterItem.getTaskID(),
            rosterItem.getTaskType(),
            rosterItem.getExecutorURI().getValue().toString());
    }
}


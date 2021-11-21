package ch.unisg.roster.roster.adapter.out.persistence.mongodb;


import ch.unisg.roster.roster.application.port.in.AddRosterItemPort;
import ch.unisg.roster.roster.application.port.in.DeleteRosterItem;
import ch.unisg.roster.roster.application.port.in.LoadRosterItemPort;
import ch.unisg.roster.roster.domain.RosterItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RosterPersistenceAdapter implements AddRosterItemPort, LoadRosterItemPort, DeleteRosterItem {

    @Autowired
    private final RosterRepository rosterRepository;
    private final RosterMapper rosterMapper;

    @Override
    public void addRosterItem(RosterItem rosterItem){
        MongoRosterDocument mongoRosterDocument = rosterMapper.mapToMongoDocument(rosterItem);
        rosterRepository.save(mongoRosterDocument);
    }

    @Override
    public RosterItem loadRosterItem(String taskId){
        MongoRosterDocument mongoRosterDocument = rosterRepository.findByTaskId(taskId);
        RosterItem rosterItem = rosterMapper.maptoDomainEntity(mongoRosterDocument);
        return rosterItem;
    }

    @Override
    public List<RosterItem> loadAllRosterItems(){
        List<MongoRosterDocument> rosterList = rosterRepository.findAll();
        List<RosterItem> rosterItemList = new ArrayList<>();
        for(MongoRosterDocument rosterDocument : rosterList){
            rosterItemList.add(rosterMapper.maptoDomainEntity(rosterDocument));
        }
        return rosterItemList;
    }

    @Override
    public void deleteRosterItem(String taskId){
        rosterRepository.deleteById(taskId);
    }
}

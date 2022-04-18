package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr00_00_updateBySaveEntity_noTnx;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr00_00_SimpleService {

    private final SimpleRepository simpleRepository;

    // No transaction here
    public SimpleEntity insertAndUpdate(String name) {
        log.info("This will execute one query: 'INSERT ...'");
        SimpleEntity simpleEntity = new SimpleEntity(name);
        simpleRepository.save(simpleEntity);

        log.info("This will execute 3 queries: 'SELECT ... by PK', 'UPDATE ... by PK', 'SELECT ... by PK");
        simpleEntity.setName(name + "Edited_" + UUID.randomUUID());
        simpleEntity = simpleRepository.save(simpleEntity);
        return simpleEntity;
    }
}

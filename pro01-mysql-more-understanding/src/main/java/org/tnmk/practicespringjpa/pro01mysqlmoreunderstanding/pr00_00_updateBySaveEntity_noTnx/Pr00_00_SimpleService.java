package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr00_00_updateBySaveEntity_noTnx;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr00_00_SimpleService {

    private final SimpleRepository simpleRepository;

    // No transaction here
    public SimpleEntity insertAndUpdate(String initName, String updateName) {
        log.info("This will execute one query: 'INSERT ...'");
        String uniqueCode = "Code_" + UUID.randomUUID();
        SimpleEntity simpleEntity = new SimpleEntity(uniqueCode, initName);
        simpleRepository.save(simpleEntity);

        log.info("This will execute 2 queries: 'SELECT ... by PK', 'UPDATE ... by PK'");
        simpleEntity.setName(updateName);
        simpleEntity = simpleRepository.save(simpleEntity);
        return simpleEntity;
    }
}

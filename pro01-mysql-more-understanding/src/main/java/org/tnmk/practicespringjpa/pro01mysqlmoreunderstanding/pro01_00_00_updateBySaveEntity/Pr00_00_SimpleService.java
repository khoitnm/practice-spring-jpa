package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_00_00_updateBySaveEntity;

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

    public SimpleEntity insert(String initName) {
        log.info("Insert entity with name {}...", initName);
        String uniqueCode = "Code_" + UUID.randomUUID();
        SimpleEntity simpleEntity = new SimpleEntity(uniqueCode, initName);
        return simpleRepository.save(simpleEntity);
    }
    // No transaction here
    public SimpleEntity update(long entityId, String updateName) {
        log.info("Update name {} by saving entity...", updateName);
        SimpleEntity simpleEntity = simpleRepository.findById(entityId).get();

        simpleEntity.setName(updateName);
        simpleEntity = simpleRepository.save(simpleEntity);
        return simpleEntity;
    }
}

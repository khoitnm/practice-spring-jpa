package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr01_00_fullUpdate_UnexpectedData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr01_00_CreateEntityService {

    private final SimpleRepository simpleRepository;

    public SimpleEntity insert(String initName) {
        String uniqueCode = "Code" + UUID.randomUUID();
        SimpleEntity simpleEntity = new SimpleEntity(uniqueCode, initName);
        simpleRepository.save(simpleEntity);

        log.info("Created entity {}", simpleEntity);
        return simpleEntity;
    }
}

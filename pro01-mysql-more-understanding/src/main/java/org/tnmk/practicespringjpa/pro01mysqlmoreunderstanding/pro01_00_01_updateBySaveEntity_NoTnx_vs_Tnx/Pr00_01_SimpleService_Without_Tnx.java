package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_00_01_updateBySaveEntity_NoTnx_vs_Tnx;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr00_01_SimpleService_Without_Tnx {

    private final SimpleRepository simpleRepository;

    public SimpleEntity insertAndUpdate(String initName, String updateName) {
        String uniqueCode = "Code"+UUID.randomUUID();
        SimpleEntity simpleEntity = new SimpleEntity(uniqueCode, initName);
        simpleRepository.save(simpleEntity);

        simpleEntity.setName(updateName);
        simpleEntity = simpleRepository.save(simpleEntity);
        return simpleEntity;
    }
}

package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pro01_01_fullUpdate_vs_partialUpdate.pr01_00_fullUpdate_UnexpectedData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleRepository;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr01_00_ChangeName_byFullUpdate_Service {
    private final SimpleRepository simpleRepository;

    @Async
    public CompletableFuture<Void> async_NoTnx_ChangeName(long entityId, String updateName) {
        log.info("async_NoTnx_ChangeName: findById {}", entityId);
        SimpleEntity simpleEntity = simpleRepository.findById(entityId).get();
        simpleEntity.setName(updateName);

        log.info("async_NoTnx_ChangeName: update name '{}' by saving entity", updateName);
        simpleRepository.save(simpleEntity);
        return CompletableFuture.completedFuture(null);
    }
}

package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr01_fullUpdate_vs_partialUpdate.pr01_00_fullUpdate_UnexpectedData;

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
        SimpleEntity simpleEntity = simpleRepository.findById(entityId).get();
        simpleEntity.setName(updateName);
        simpleRepository.save(simpleEntity);
        return CompletableFuture.completedFuture(null);
    }
}

package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr00_01_updateBySaveEntity_Tnx;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleRepository;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr00_00_updateBySaveEntity_noTnx.Pr00_00_SimpleService;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pr00_01_SimpleService {

    private final SimpleRepository simpleRepository;

    /**
     * This logic is exactly the same as {@link Pr00_00_SimpleService}.
     * The only difference is this code run inside a transaction.
     */
    @Transactional
    public SimpleEntity insertAndUpdate(String name) {
        log.info("This will execute one query: 'INSERT ...'");
        SimpleEntity simpleEntity = new SimpleEntity(name);
        simpleRepository.save(simpleEntity);

        log.info("This will execute 2 queries: 'UPDATE ... by PK', 'SELECT ... by PK.\n" +
                "Compare to Pr00_00_SimpleService, " +
                "this updating logic doesn't need an additional SELECT before UPDATING " +
                "(because it's running inside a transaction)");
        simpleEntity.setName(name + "Edited_" + UUID.randomUUID());
        simpleEntity = simpleRepository.save(simpleEntity);

        log.info("The interesting this is: \n" +
                "The 'UPDATE...' statement is executed at the end of the transaction, right before it's committed).\n" +
                "It means even though we triggered `simpleRepository.save()` before this log,\n" +
                "the 'UPDATE ...' statement is executed only after this log.");
        return simpleEntity;
    }
}

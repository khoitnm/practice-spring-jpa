package org.tnmk.practicespringjpa.pro10transaction.practice_05_05_transaction_nodeadlock_when_saveEntityRollbackProblem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transaction.common.utils.ThreadUtils;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class Pr05_05_ExternalSystemSimulator {
    private final Set<SimpleEntity> data = new HashSet<>();

    public void addItem(SimpleEntity simpleEntity, int runtimeMillis) {
        log.info("jpaSaveEntity: assume save data into another system via API call: starting\n" +
                "\tAnd if the previous jpaSave failed (for whatever reason), no data is saved into the other system.\n" +
                "\tIf the previous jpaSave succeed, we'll try to save data into the other system." +
                "\tAnd if we cannot save data into the other system, rollback the data that saved into DB.");
        data.add(simpleEntity);
        ThreadUtils.sleep(runtimeMillis);
        log.info("jpaSaveEntity: assume save data into another system via API call: finished!!!\n" +
                "\tIf we see this log, it means we cannot rollback data in the other system anymore.");
    }

    public boolean hasItem(String itemName) {
        log.info("Current data:\n{}", data);
        return data.stream().anyMatch(item -> item.getName().equals(itemName));
    }
}

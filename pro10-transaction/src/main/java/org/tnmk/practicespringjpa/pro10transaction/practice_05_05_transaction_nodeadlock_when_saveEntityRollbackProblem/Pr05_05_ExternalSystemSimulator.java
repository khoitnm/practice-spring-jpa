package org.tnmk.practicespringjpa.pro10transaction.practice_05_05_transaction_nodeadlock_when_saveEntityRollbackProblem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.tnmk.practicespringjpa.pro10transaction.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro10transaction.common.utils.ThreadUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class Pr05_05_ExternalSystemSimulator {
    private final Set<SimpleEntity> data = new HashSet<>();

    public void addEntity(SimpleEntity simpleEntity, int runtimeMillis) {
        data.add(simpleEntity);
        ThreadUtils.sleep(runtimeMillis);
    }

    public Optional<SimpleEntity> findEntityByName(String itemName) {
        log.info("Current data in externalSystem:\n{}", data);
        return data.stream().filter(item -> item.getName().equals(itemName)).findAny();
    }
}

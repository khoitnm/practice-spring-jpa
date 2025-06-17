package org.tnmk.practice_spring_jpa.pro07_multi_tenant_row_level.support_for_testing.drop_dbusers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Service
@RequiredArgsConstructor
public class DropDbUsersInManyBatchesService {

    private static final int BATCH_SIZE = 10;

    private final DropDbUsersInOneBatchService dropDbUsersInOneBatchService;

    public void dropUsersInManyBatches(List<String> usersToDrop) throws ExecutionException, InterruptedException {
        try (ExecutorService executorService = Executors.newFixedThreadPool(4);) {
            List<Future<?>> futures = new ArrayList<>();

            for (int i = 0; i < usersToDrop.size(); i += BATCH_SIZE) {
                final int startIndex = i;

                List<String> batchUsernames = usersToDrop.subList(i, Math.min(i + BATCH_SIZE, usersToDrop.size()));
                futures.add(executorService.submit(() -> {
                    log.info("Dropping {} users, starting from [{}]... ", batchUsernames.size(), startIndex);
                    try {
                        dropDbUsersInOneBatchService.dropUsersInOneBatch(batchUsernames);
                    } catch (SQLException e) {
                        throw new RuntimeException("Error dropping users batch: " + batchUsernames, e);
                    }
                }));
            }

            // Wait for all tasks to complete
            for (Future<?> future : futures) {
                future.get();
            }

            executorService.shutdown();
        }
    }
}
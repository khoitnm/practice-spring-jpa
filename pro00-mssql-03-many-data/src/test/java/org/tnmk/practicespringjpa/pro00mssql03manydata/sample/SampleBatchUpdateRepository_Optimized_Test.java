package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro00mssql03manydata.testinfra.BaseSpringTest_WithActual_MsSqlServer;

import java.util.List;

@Slf4j
public class SampleBatchUpdateRepository_Optimized_Test extends BaseSpringTest_WithActual_MsSqlServer {
    private static final int UPDATE_COUNTS = 50;

    @Autowired
    private ChangeEntityNameService changeEntityNameService;

    @Autowired
    private SampleBatchUpdateRepository_Optimized sampleBatchUpdateRepositoryOptimized;

    /**
     * Before running this test case, please run the script in
     * `practice-spring-jpa\pro00-mssql-03-many-data\support-scripts\01_random_tbl_sample_entity.sql`
     * to insert sample data into this table.
     */
    @Test
    public void updateTopItems_Approach04_Successfully() {
        List<SampleEntity> itemsToBeUpdated =
            changeEntityNameService.changeRandomNamesAndEntityCodesForTopItems("Approach04B_name", "Approach04B_code", UPDATE_COUNTS);

        sampleBatchUpdateRepositoryOptimized.updateNamesForEntities_Approach04(itemsToBeUpdated);
        log.info("itemsToBeUpdated: \n{}", itemsToBeUpdated.size());
    }

    @Test
    public void updateTopItems_Approach04B_withSomeErrorItems() {
        List<SampleEntity> itemsToBeUpdated =
            changeEntityNameService.changeRandomNamesAndEntityCodesForTopItems_withSomeErrorItems("Approach04B_name", "Approach04B_code", UPDATE_COUNTS);

        UpdateResult result = sampleBatchUpdateRepositoryOptimized.updateNamesForEntities_Approach04B_withReportedResultForEachGroup(itemsToBeUpdated);
        log.info("itemsToBeUpdated: \n{}", result);
        Assertions.assertTrue(!result.getErrorItems().isEmpty());
    }
}

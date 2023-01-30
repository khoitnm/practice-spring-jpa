package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro00mssql03manydata.testinfra.BaseSpringTest_WithActual_MsSqlServer;

import java.util.List;

@Slf4j
public class SampleBatchUpdateRepository_Optimized_Test extends BaseSpringTest_WithActual_MsSqlServer {
    private static final int UPDATE_COUNTS = 1000;

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
            changeEntityNameService.changeRandomNamesForTopItems("Approach04", UPDATE_COUNTS);

        sampleBatchUpdateRepositoryOptimized.updateNamesForEntities_Approach04(itemsToBeUpdated);
        log.info("itemsToBeUpdated: \n{}", itemsToBeUpdated.size());
    }
}

package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.support_for_testing.drop_dbusers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.support_for_testing.SimpleJdbcHelper;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
// This test doesn't care about other tables in the DB, hence no need to care about Flyway script.
@SpringBootTest(properties = {"spring.flyway.enabled=false"})
public class DropDbUsersApplication {

    @Autowired
    SimpleJdbcHelper simpleJdbcHelper;

    @Autowired
    private DropDbUsersInManyBatchesService dropDbUsersInManyBatchesService;

    @Test
    public void dropUsersInManyBatches() throws ExecutionException, InterruptedException {
        List<String> usersToDrop = simpleJdbcHelper.findMany(new SingleColumnRowMapper<>(String.class), "SELECT name FROM sys.database_principals WHERE type = 'S' AND (name LIKE 'stressTenant%' OR name LIKE 'SampleService%' OR name LIKE 'rollbackTenant%')");
        dropDbUsersInManyBatchesService.dropUsersInManyBatches(usersToDrop);
    }
}
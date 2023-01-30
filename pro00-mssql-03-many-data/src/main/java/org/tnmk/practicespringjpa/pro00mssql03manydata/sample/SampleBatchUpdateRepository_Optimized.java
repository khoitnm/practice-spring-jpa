package org.tnmk.practicespringjpa.pro00mssql03manydata.sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SampleBatchUpdateRepository_Optimized {
    private static final int PARAM_INDEX_STARTING = 1;

    // Max params in a query (defined by MS SQL DB) = 2100.
    // 1 items takes 5 params (there are 2 fields, each field needs 2 params, plus one param for id placeholder in WHERE statement).
    // So 1 query take max 2100/5 = 420 items
    // to make it safe, I'll put 419 items.
    private static final int MAX_UPDATE_ITEMS_IN_ONE_QUERY = 419;
    private final JdbcTemplate jdbcTemplate;

    /**
     * This approach is actually similar to {@link SampleBatchUpdateRepository#updateNamesForEntities_Approach03(List)}.
     * The main difference is approach03 having risk of SQL injections attack.
     * So this approach use PreparedStatement instead of concating string into query to avoid that risk.
     */
    public void updateNamesForEntities_Approach04(List<SampleEntity> sampleEntities) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // These are groups of sample entities in which each group are merged into one SQL query.
        List<List<SampleEntity>> sampleEntityGroups = ListUtils.partition(sampleEntities, MAX_UPDATE_ITEMS_IN_ONE_QUERY);
        for (List<SampleEntity> sampleEntityGroup : sampleEntityGroups) {
            String query = createQueryForUpdateNamesAndCodes(sampleEntityGroup.size());
            PreparedStatementSetter preparedStatementCallback = createPreparedStatementForUpdateNamesAndCodes(sampleEntityGroup);
            jdbcTemplate.update(query, preparedStatementCallback);
        }

        stopWatch.stop();
        logRuntime("updateNamesForEntities_Approach04", stopWatch);
    }

    /**
     * The final query will be like this:
     * <pre>
     * <code>
     * UPDATE sample_entity SET
     *    name = CASE                   -- [fieldIndex: y = 0]:
     *      WHEN id = 0 THEN 'Name0'    -- [itemIndex: i = 0]: param[0] & param[1] == param[i*2] & param[i*2 + 1]
     *      WHEN id = 1 THEN 'Name1'    -- [itemIndex: i = 1]: param[2] & param[3] == param[i*2] & param[i*2 + 1]
     *      ...
     *      WHEN id = N THEN 'NameN'    -- [itemIndex: i = N]: param[N*2] & param[N*2+1]
     *    END,
     *    entity_code = CASE            -- [fieldIndex: y = 1]:
     *      WHEN id = 0 THEN 'Code0'    -- [itemIndex: i = 0]: param[N*2+1 + 1 + 0]    & param[N*2+1 + 1 + 1] == param[(N*2+1)*y + y + (i*2)] &  == param[(N*2+1)*y + y + (i*2+1)]
     *      WHEN id = 1 THEN 'Code1'    -- [itemIndex: i = 1]: param[N*2+1 + 1 + 2]    & param[N*2+1 + 1 + 3] == param[(N*2+1)*y + y + (i*2)] &  == param[(N*2+1)*y + y + (i*2+1)]
     *      ...
     *      WHEN id = N THEN 'CodeN'    -- [itemIndex: i = N]: param[N*2+1 + 1 + N*2]  & param[N*2+1 + 1 + N*2+1]
     *    END
     * WHERE id IN (
     *    0,                            -- [itemIndex: i = 0] params[(N*2+1)*y+y+(i*2+1) + 1 + 0] == params[(N*2+1)*y+y+(i*2+1) + i]
     *    1,                            -- [itemIndex: i = 1] params[(N*2+1)*y+y+(i*2+1) + 1 + 1]
     *    ...,
     *    N                             -- [itemIndex: i = N] params[(N*2+1)*y+y+(i*2+1) + 1 + N]
     * )
     * </code>
     * </pre>
     */
    private String createQueryForUpdateNamesAndCodes(int entitiesCount) {
        StringBuilder query = new StringBuilder("UPDATE sample_entity SET ");
        StringBuilder whenAndThenPart = new StringBuilder();
        for (int i = 0; i < entitiesCount; i++) {
            whenAndThenPart.append("  WHEN id=? THEN ? \n");
        }
        query.append("name = CASE \n").append(whenAndThenPart).append("END, \n");
        query.append("entity_code = CASE \n").append(whenAndThenPart).append("END \n");

        String idsPlaceholder =
            IntStream.range(0, entitiesCount).mapToObj(entity -> "?")
                .collect(Collectors.joining(", "));
        query.append(String.format("WHERE id IN (%s) ", idsPlaceholder));
        return query.toString();
    }

    /**
     * This method create prepared statements for the query which is created from
     * {@link #createQueryForUpdateNamesAndCodes(int)}
     */
    private PreparedStatementSetter createPreparedStatementForUpdateNamesAndCodes(List<SampleEntity> sampleEntities) {
        PreparedStatementSetter preparedStatement = ps -> {
            int paramIndex = PARAM_INDEX_STARTING;
            // Values for updating `name` field
            for (SampleEntity sampleEntity : sampleEntities) {
                ps.setLong(paramIndex++, sampleEntity.getId());
                ps.setString(paramIndex++, sampleEntity.getName());
            }

            // Values for updating `entity_code` field
            for (SampleEntity sampleEntity : sampleEntities) {
                ps.setLong(paramIndex++, sampleEntity.getId());
                ps.setString(paramIndex++, sampleEntity.getEntityCode());
            }

            // Values for ids placeholder.
            for (SampleEntity sampleEntity : sampleEntities) {
                ps.setLong(paramIndex++, sampleEntity.getId());
            }
        };

        return preparedStatement;
    }

    private void logRuntime(String messageDescription, StopWatch stopWatch) {
        log.info(messageDescription + " Runtime: {} millis ({} secs)",
            stopWatch.getTotalTimeMillis(),
            (double) stopWatch.getTotalTimeMillis() / 1000d);
    }
}

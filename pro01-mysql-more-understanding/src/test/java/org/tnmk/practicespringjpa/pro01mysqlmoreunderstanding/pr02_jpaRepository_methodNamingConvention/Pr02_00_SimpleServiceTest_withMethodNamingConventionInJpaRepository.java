package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.pr02_jpaRepository_methodNamingConvention;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.common.SimpleEntity;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.testinfra.BaseSpringTest_WithActualDb;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class Pr02_00_SimpleServiceTest_withMethodNamingConventionInJpaRepository extends BaseSpringTest_WithActualDb {
    @Autowired
    private Pr02_00_SimpleRepository simpleRepository;

    @Test
    public void test_ItemsWithSameNameAreDeletedSuccessfully() {
        // Given
        String entityName = "ManyItemsWithUseThisSameName";
        createEntitiesWithSameName(2, entityName);

        // When
        log.info("Delete all items with the name '{}': starting...\n" +
                        "One interesting thing is:\n" +
                        "\tYou'll see that in the log, it won't execute something like this 'DELETE FROM simple_entity WHERE name = ?'\n" +
                        "\tInstead, it executed many queries:\n" +
                        "\t\t   1. Select all items satisfy the name\n" +
                        "\t\t   2. Loop through all of items one-by-one, and deleting each of them by separate 'DELETE' statements."
                , entityName);
        simpleRepository.deleteByNameIsLike(entityName);
        log.info("Delete all items with the name '{}': end!!!", entityName);

        // Then
        assert_EntitiesWithName_NoExistAnyMore(entityName);
    }

    private List<SimpleEntity> createEntitiesWithSameName(int entitiesCount, String entityName) {
        List<SimpleEntity> result = IntStream.range(0, entitiesCount)
                .mapToObj(i -> {
                    SimpleEntity entity = new SimpleEntity("Code" + UUID.randomUUID(), entityName);
                    return simpleRepository.save(entity);
                }).collect(Collectors.toList());
        return result;
    }

    private void assert_EntitiesWithName_NoExistAnyMore(String expectName) {
        List<SimpleEntity> actualEntitiesInDB = simpleRepository.findByName(expectName);
        Assertions.assertTrue(actualEntitiesInDB.isEmpty());
    }
}

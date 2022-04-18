package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.testinfra;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.Pro01MysqlMoreUnderstandingApplication;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.testinfra.dbtestcontainer.DBContainerContextInitializer;

@ActiveProfiles("testcontainer")
@SpringBootTest(classes = { Pro01MysqlMoreUnderstandingApplication.class })
@ContextConfiguration(initializers = DBContainerContextInitializer.class)
public abstract class BaseSpringTest_WithTestContainer {
}

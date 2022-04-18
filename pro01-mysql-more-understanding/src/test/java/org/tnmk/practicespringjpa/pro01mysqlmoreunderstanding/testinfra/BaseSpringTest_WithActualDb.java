package org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.testinfra;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.tnmk.practicespringjpa.pro01mysqlmoreunderstanding.Pro01MysqlMoreUnderstandingApplication;

@ActiveProfiles("test")
@SpringBootTest(classes = { Pro01MysqlMoreUnderstandingApplication.class })
//@ContextConfiguration(initializers = DBContainerContextInitializer.class)
public abstract class BaseSpringTest_WithActualDb {
}

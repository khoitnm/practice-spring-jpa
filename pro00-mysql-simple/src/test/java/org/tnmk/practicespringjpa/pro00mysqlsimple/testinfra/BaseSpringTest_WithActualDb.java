package org.tnmk.practicespringjpa.pro00mysqlsimple.testinfra;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.tnmk.practicespringjpa.pro00mysqlsimple.Pro00MysqlSimpleApplication;

@ActiveProfiles("test")
@SpringBootTest(classes = { Pro00MysqlSimpleApplication.class })
//@ContextConfiguration(initializers = DBContainerContextInitializer.class)
public abstract class BaseSpringTest_WithActualDb {
}

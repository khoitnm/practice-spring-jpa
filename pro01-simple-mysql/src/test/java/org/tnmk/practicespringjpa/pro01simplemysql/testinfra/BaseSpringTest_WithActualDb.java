package org.tnmk.practicespringjpa.pro01simplemysql.testinfra;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.tnmk.practicespringjpa.pro01simplemysql.Pro01SimpleMysqlApplication;

@ActiveProfiles("test")
@SpringBootTest(classes = { Pro01SimpleMysqlApplication.class })
//@ContextConfiguration(initializers = DBContainerContextInitializer.class)
public abstract class BaseSpringTest_WithActualDb {
}

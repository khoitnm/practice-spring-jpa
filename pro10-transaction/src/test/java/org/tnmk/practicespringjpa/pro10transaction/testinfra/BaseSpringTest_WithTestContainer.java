package org.tnmk.practicespringjpa.pro10transaction.testinfra;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.tnmk.practicespringjpa.pro10transaction.Pro10TransactionApplication;
import org.tnmk.practicespringjpa.pro10transaction.testinfra.dbtestcontainer.DBContainerContextInitializer;

@ActiveProfiles("testcontainer")
@SpringBootTest(classes = { Pro10TransactionApplication.class })
@ContextConfiguration(initializers = DBContainerContextInitializer.class)
public abstract class BaseSpringTest_WithTestContainer {
}

package org.tnmk.practicespringjpa.pro10transactionsimple.testinfra;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.tnmk.practicespringjpa.pro10transactionsimple.Pro10TransactionSimpleApplication;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.dbtestcontainer.DBContainerContextInitializer;

@ActiveProfiles("testcontainer")
@SpringBootTest(classes = { Pro10TransactionSimpleApplication.class })
@ContextConfiguration(initializers = DBContainerContextInitializer.class)
public abstract class BaseSpringTest_WithTestContainer {
}

package org.tnmk.practicespringjpa.pro10transactionsimple.testinfra;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practicespringjpa.pro10transactionsimple.Pro10TransactionSimpleApplication;
import org.tnmk.practicespringjpa.pro10transactionsimple.testinfra.dbtestcontainer.DBContainerContextInitializer;

@ActiveProfiles("testcontainer")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Pro10TransactionSimpleApplication.class})
@ContextConfiguration(initializers = DBContainerContextInitializer.class)
@Ignore
public abstract class BaseSpringTest_WithTestContainer {
}
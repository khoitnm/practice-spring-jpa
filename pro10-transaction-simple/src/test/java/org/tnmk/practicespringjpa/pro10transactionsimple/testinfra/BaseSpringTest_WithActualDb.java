package org.tnmk.practicespringjpa.pro10transactionsimple.testinfra;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.tnmk.practicespringjpa.pro10transactionsimple.Pro10TransactionSimpleApplication;

@ActiveProfiles("test")
@SpringBootTest(classes = { Pro10TransactionSimpleApplication.class })
//@ContextConfiguration(initializers = DBContainerContextInitializer.class)
public abstract class BaseSpringTest_WithActualDb {
}

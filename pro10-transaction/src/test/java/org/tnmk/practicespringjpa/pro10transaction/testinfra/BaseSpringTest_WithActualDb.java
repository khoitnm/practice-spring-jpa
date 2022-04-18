package org.tnmk.practicespringjpa.pro10transaction.testinfra;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.tnmk.practicespringjpa.pro10transaction.Pro10TransactionApplication;

@ActiveProfiles("test")
@SpringBootTest(classes = { Pro10TransactionApplication.class })
//@ContextConfiguration(initializers = DBContainerContextInitializer.class)
public abstract class BaseSpringTest_WithActualDb {
}

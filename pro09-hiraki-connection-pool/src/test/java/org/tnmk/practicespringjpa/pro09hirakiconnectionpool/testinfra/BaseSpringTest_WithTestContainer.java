package org.tnmk.practicespringjpa.pro09hirakiconnectionpool.testinfra;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practicespringjpa.pro09hirakiconnectionpool.Pro09HirakiConnectionPoolApplication;
import org.tnmk.practicespringjpa.pro09hirakiconnectionpool.testinfra.dbtestcontainer.DBContainerContextInitializer;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Pro09HirakiConnectionPoolApplication.class})
@ContextConfiguration(initializers = DBContainerContextInitializer.class)
@Ignore
public abstract class BaseSpringTest_WithTestContainer {
}

package org.tnmk.practicespringjpa.pro07multitenant.testinfra;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practicespringjpa.Pro07MultiTenantApplication;
import org.tnmk.practicespringjpa.pro07multitenant.testinfra.dbtestcontainer.DBContainerContextInitializer;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Pro07MultiTenantApplication.class})
@ContextConfiguration(initializers = DBContainerContextInitializer.class)
@Ignore
public abstract class BaseSpringTest {
}

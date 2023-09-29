package org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.testinfra;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.Pro00Mssql04CombineQueryModelApplication;
import org.tnmk.practicespringjpa.pro00mssql04combinequerymodel.testinfra.dbtestcontainer.DBContainerContextInitializer;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Pro00Mssql04CombineQueryModelApplication.class})
@ContextConfiguration(initializers = DBContainerContextInitializer.class)
@Ignore
public abstract class BaseSpringTest_WithTestContainer {
}

package org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.testinfra;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practicespringjpa.pro00mssql01jdbcbatch.Pro00Mssql01JdbcBatchApplication;

@ActiveProfiles("mysql")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Pro00Mssql01JdbcBatchApplication.class})
//@ContextConfiguration(initializers = DBContainerContextInitializer.class)
@Ignore
public abstract class BaseSpringTest_WithActual_MySql {
}

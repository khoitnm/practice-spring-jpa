package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.testinfra;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.Pro00Mssql02MoreUnderstandingApplication;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Pro00Mssql02MoreUnderstandingApplication.class})
//@ContextConfiguration(initializers = DBContainerContextInitializer.class)
@Ignore
public abstract class BaseSpringTest {
}

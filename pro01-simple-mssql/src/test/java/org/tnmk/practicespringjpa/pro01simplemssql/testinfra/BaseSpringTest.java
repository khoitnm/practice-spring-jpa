package org.tnmk.practicespringjpa.pro01simplemssql.testinfra;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practicespringjpa.pro01simplemssql.Pro01SimpleMssqlApplication;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Pro01SimpleMssqlApplication.class})
//@ContextConfiguration(initializers = DBContainerContextInitializer.class)
@Ignore
public abstract class BaseSpringTest {
}

package org.tnmk.practicespringjpa.pro08mssqlsimple.testinfra;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practicespringjpa.pro08mssqlsimple.Pro08MssqlSimpleApplication;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Pro08MssqlSimpleApplication.class})
//@ContextConfiguration(initializers = DBContainerContextInitializer.class)
@Ignore
public abstract class BaseSpringTest {
}

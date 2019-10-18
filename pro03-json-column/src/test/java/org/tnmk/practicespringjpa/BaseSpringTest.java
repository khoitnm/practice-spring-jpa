package org.tnmk.practicespringjpa;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practicespringjpa.common.testcontainer.DBContainerContextInitializer;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JsonColumnApplication.class})
@ContextConfiguration(initializers = DBContainerContextInitializer.class)
@Ignore
public abstract class BaseSpringTest {
}
